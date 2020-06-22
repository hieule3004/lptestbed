package com.lpinc.testbed.simulator.runner;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.contract.Category;
import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.utils.Domain;
import com.lpinc.testbed.simulator.utils.Repeat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ParallelRunner {

  private static final int runs = 50;

  private Simulator[][] simulators;
//  private DimensionLock lock;

  public ParallelRunner(Simulator[][] simulators) {
    this.simulators = simulators;
//    this.lock = new DimensionLock(simulators.length, simulators[0].length);
  }

  public double[][][] getCalcTable() {
    double[][][] calcTable = new double[simulators.length][simulators[0].length][];
    Repeat.parallel(simulators.length, i ->
        Repeat.parallel(simulators[0].length, j ->
            calcTable[i][j] = simulators[i][j].getAverage()));
    return calcTable;
  }

  public double[][][] getRatingTable() {
    double[][][] ratings = new double[simulators.length][simulators[0].length][];
    Repeat.parallel(simulators.length, i ->
        Repeat.parallel(simulators[0].length, j -> {
          List<Double[]> list = simulators[i][j].getRatings();
          ratings[i][j] = IntStream.range(0, list.get(0).length).mapToDouble(k ->
              Domain.squeeze(0, 1, list.get(list.size() - 1)[k]
                  + getAgent(simulators[i][j]).get(k).getJudgement()
              ))
              .toArray();
        }));
    return ratings;
  }

  public double[][] getRowAvg(double[][][] ratingTable) {
    return Arrays.stream(ratingTable)
        .map(row -> Arrays.stream(Arrays.stream(row).reduce(new double[row[0].length], (a, b) ->
            IntStream.range(0, a.length).mapToDouble(k -> a[k] + b[k])
                .toArray())).map(v -> v / row.length).toArray())
        .toArray(double[][]::new);
  }

  public double[][] getColumnAvg(double[][][] ratingTable) {
    return IntStream.range(0, ratingTable[0].length).mapToObj(j ->
        Arrays.stream(Arrays.stream(ratingTable).map(row -> row[j])
            .reduce(new double[ratingTable[0][j].length], (a, b) ->
                IntStream.range(0, a.length).mapToDouble(k -> a[k] + b[k])
                    .toArray())).map(v -> v / ratingTable.length).toArray())
        .toArray(double[][]::new);
  }

  private List<Agent> getAgent(Simulator simulator) {
    return simulator.getContract().clausesList().stream()
        .map(Clause::getOtherAgent).collect(Collectors.toList());
  }

  public Map<Agent, Double> run() {
    for (int n = 0; n < runs; n++) {
      Repeat.parallel(simulators.length, i ->
          Repeat.parallel(simulators[0].length, j ->
              Repeat.sequential(runs, simulators[i][j]::run)));

      double[][][] ratingTable = getRatingTable();
      double[][] avgs = getRowAvg(ratingTable);

      Repeat.sequential(simulators.length, i ->
          Repeat.sequential(simulators[0].length, j -> {
            List<Agent> agents = simulators[i][j].getContract().getAgents();
            Repeat.sequential(agents.size(), k -> {
              agents.get(k).updateJudgement(ratingTable[i][j][k] - avgs[i][k]);
              int frequency = Category.getFrequency(agents.get(k).getClass());
              if (runs % (simulators[i][j].getContract().getDuration() * frequency) == 0) {
                agents.get(k).updateRatingCount(frequency);
              }
            });
          }));
    }

    double[][][] ratingTable = getRatingTable();
    double[][] rowAvg = getRowAvg(ratingTable);
    Map<Agent, Double> consumerTrust = IntStream.range(0, rowAvg.length).boxed()
        .collect(Collectors.toMap(i -> simulators[i][0].getContract().getAgents().get(1),
            i -> rowAvg[i][0]));
    Map<Agent, Double> result = new LinkedHashMap<>(consumerTrust);
    Map<Agent, List<Double>> trusts = new LinkedHashMap<>();
    double[][] columnAvg = getColumnAvg(ratingTable);
    Repeat.sequential(columnAvg.length, j -> {
      List<Double> list = trusts
          .getOrDefault(simulators[0][j].getContract().getAgents().get(0), new ArrayList<>());
      list.add(columnAvg[j][1]);
      trusts.put(simulators[0][j].getContract().getAgents().get(0), list);
    });
    Map<Agent, Double> providerTrust = trusts.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey,
            e -> e.getValue().stream().reduce(0.0, Double::sum) / e.getValue().size()));
    result.putAll(providerTrust);
    return result;
  }
}
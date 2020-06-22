package com.lpinc.testbed.simulator.runner;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.request.Request;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.resource.Resource;
import com.lpinc.testbed.simulator.utils.Splitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Simulator {

  private final Map<Clause, List<Response<?>>> log;
  private final Map<Agent, Splitter<Double>> agentData;
  private final Map<Resource, Splitter<Double>> resourceData;
  private final List<Request> events;
  private final List<Double[]> ratings;
  private final Contract contract;

  public Simulator(Contract contract) {
    this.contract = contract;
    this.log = new LinkedHashMap<>();
    this.agentData = new HashMap<>();
    this.resourceData = new HashMap<>();
    this.events = new ArrayList<>();
    this.ratings = new ArrayList<>();

    contract.clausesList().forEach(c -> events.addAll(contract.getObligations(c)));
    events.sort(Event::compareTo);

    for (int i = 0; i < contract.length(); i++) {
      Clause clause = contract.clausesList().get(i);
      events.addAll(contract.getObligations(clause));
      log.put(clause, new ArrayList<>());
      Agent agent = clause.getAgent();
      agentData.put(agent, new Splitter<>(agent.getData(), events.size()));
      Resource resource = clause.getResource();
      resourceData.put(resource, new Splitter<>(resource.getData(), events.size()));
    }
  }

  public Splitter<Double> getDataOf(Agent agent) {
    return agentData.get(agent);
  }

  public Splitter<Double> getDataOf(Resource resource) {
    return resourceData.get(resource);
  }

  public double[] getAverage() {
    return log.values().stream().mapToDouble(r ->
        r.parallelStream().map(Response::evaluate).reduce(0.0, Double::sum) / r.size()
    ).toArray();
  }

  public void run(int branch) {
    Map<Clause, List<Double>> values = new LinkedHashMap<>();
    contract.clausesList().forEach(c -> values.put(c, new ArrayList<>()));
    events.forEach(obligation -> {
      Response<?> response = obligation.check(this, branch);
      log.get(obligation.getClause()).add(response);
      values.get(obligation.getClause()).add(response.evaluate());
    });
    ratings.add(values.values().stream()
        .map(l -> l.stream().reduce(0.0, Double::sum) / l.size())
        .toArray(Double[]::new));
  }

  public List<Double[]> getRatings() {
    return ratings;
  }

  public Contract getContract() {
    return contract;
  }
}

package com.lpinc.testbed.simulator.runner;

import com.lpinc.testbed.simulator.utils.DimensionLock;
import com.lpinc.testbed.simulator.utils.Repeat;

public final class ParallelRunner {

  private static final int runs = 500;

  private Simulator[][] simulators;
  private DimensionLock lock;

  public ParallelRunner(Simulator[][] simulators) {
    this.simulators = simulators;
    this.lock = new DimensionLock(simulators.length, simulators[0].length);
  }

  public double[][][] getCalcTable() {
    double[][][] calcTable = new double[simulators.length][simulators[0].length][];
    Repeat.parallel(simulators.length, i ->
        Repeat.parallel(simulators[0].length, j ->
            calcTable[i][j] = simulators[i][j].getAverage()));
    return calcTable;
  }

  public void run() {
    Repeat.parallel(simulators.length, i ->
        Repeat.parallel(simulators[0].length, j ->
            Repeat.sequential(runs, k -> {
              lock.lock(i, j);
              simulators[i][j].run(k);
              lock.unlock(i, j);
            })));
  }
}
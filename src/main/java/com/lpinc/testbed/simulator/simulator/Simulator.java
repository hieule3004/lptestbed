package com.lpinc.testbed.simulator.simulator;

import com.lpinc.testbed.simulator.agent.Consumer;
import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.principal.Provider;
import com.lpinc.testbed.simulator.resource.Resource;
import com.lpinc.testbed.simulator.utils.DimensionLock;
import com.lpinc.testbed.simulator.utils.ExitCode;
import com.lpinc.testbed.simulator.utils.Repeat;
import java.util.List;

public abstract class Simulator<P extends Provider, C extends Consumer, R extends Resource<P>> {

  private final List<C> consumers;
  private final List<R> resources;

  private final Contract<P, C, R>[][] contracts;
  private final Double[][] truthTable;
  private final DimensionLock lock;

  public Simulator(List<C> consumers, List<R> resources, Contract<P, C, R>[][] contracts) {
    this.consumers = consumers;
    this.resources = resources;
    this.contracts = contracts;
    //init
    truthTable = new Double[consumers.size()][resources.size()];
    Repeat.fill(truthTable, i -> 0.0);
    lock = new DimensionLock(consumers.size(), resources.size());
  }

  public Double[][] getTruthTable() {
    return truthTable;
  }

  public void run() {
    int runs = 5000;
    Repeat.parallel(consumers.size(), i -> Repeat.parallel(resources.size(), j -> {
      Repeat.parallel(runs, k -> {
        lock.lock(i, j);
        //trigger event and update
        ExitCode result = processEvent(contracts[i][j]);
        if (result == ExitCode.SUCCESS) {
          truthTable[i][j]++;
        }
        lock.unlock(i, j);
      });
      //calculate truth value
      truthTable[i][j] /= runs;
    }));
  }

  public abstract ExitCode processEvent(Contract<P, C, R> contract);
}

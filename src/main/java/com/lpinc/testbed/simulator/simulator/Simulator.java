package com.lpinc.testbed.simulator.simulator;

import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.agent.customer.Customer;
import com.lpinc.testbed.simulator.monitor.Monitor;
import com.lpinc.testbed.simulator.agent.provider.Provider;
import com.lpinc.testbed.simulator.resource.Resource;
import com.lpinc.testbed.simulator.utils.DimensionLock;
import com.lpinc.testbed.simulator.utils.Repeat;
import java.util.List;

public abstract class Simulator {

  private final List<? extends Customer> consumers;
  private final List<? extends Resource<?>> resources;

  private Monitor[][] monitors;
  private final DimensionLock lock;

  private <C extends Customer, R extends Resource<? extends Provider>>
    Simulator(List<C> consumers, List<R> resources, Monitor[][] monitors) {
    this.consumers = consumers;
    this.resources = resources;
    this.monitors = monitors;
    //init
    lock = new DimensionLock(consumers.size(), resources.size());
  }

  public <P extends Provider, C extends Customer, R extends Resource<P>>
    Simulator(List<C> consumers, List<R> resources, Contract<P, C, R>[][] contracts) {
    this(consumers, resources, new Monitor[consumers.size()][resources.size()]);
    for (int i = 0; i < consumers.size(); i++) {
      for (int j = 0; j < resources.size(); j++) {
        monitors[i][j] = new Monitor(contracts[i][j]);
      }
    }
  }

  public Double[][][] getCalcTable() {
    Double[][][] calcTable = new Double[consumers.size()][resources.size()][];
    for (int i = 0; i < consumers.size(); i++) {
      for (int j = 0; j < resources.size(); j++) {
        calcTable[i][j] = monitors[i][j].getValueVector();
      }
    }
    return calcTable;
  }

  public void run() {
    int runs = 500;
    Repeat.parallel(consumers.size(), i -> Repeat.parallel(resources.size(), j -> {
      Repeat.parallel(runs, k -> {
        //trigger event and update
        lock.lock(i, j);
        for (int index : generateEvents(monitors[i][j].getContract())) {
          monitors[i][j].trigger(index);
        }
        lock.unlock(i, j);
      });
      //calculate truth value
      monitors[i][j].average();
    }));
  }

  public abstract int[] generateEvents(Contract<?, ?, ?> contract);
}
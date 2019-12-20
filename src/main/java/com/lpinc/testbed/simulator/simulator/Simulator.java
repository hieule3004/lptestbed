package com.lpinc.testbed.simulator.simulator;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.resource.Resource;
import com.lpinc.testbed.simulator.utils.DimensionLock;
import com.lpinc.testbed.simulator.utils.ExitCode;
import com.lpinc.testbed.simulator.utils.Repeat;

import java.util.List;

public abstract class Simulator<A extends Agent, R extends Resource<?>> {

  private final List<A> agents;
  private final List<R> resources;

  private final Double[][] truthTable;
  private final DimensionLock lock;

  public Simulator(List<A> agents, List<R> resources) {
    this.agents = agents;
    this.resources = resources;
    //init
    truthTable = new Double[agents.size()][resources.size()];
    Repeat.fill(truthTable, i -> 0.0);
    lock = new DimensionLock(agents.size(), resources.size());
  }

  public Double[][] getTruthTable() {
    return truthTable;
  }

  public void run() {
    int runs = 5000;
    Repeat.parallel(agents.size(), i -> Repeat.parallel(resources.size(), j -> {
      Repeat.parallel(runs, k -> {
        lock.lock(i, j);
        //trigger event
        Event event = createEvent(agents.get(i), resources.get(j));
        ExitCode result = event.process();
        //update event count
          if (result == ExitCode.SUCCESS) {
              truthTable[i][j]++;
          }
        lock.unlock(i, j);
      });
      //calculate truth value
      truthTable[i][j] /= runs;
    }));
  }

  public abstract Event createEvent(A agent, R resource);
}

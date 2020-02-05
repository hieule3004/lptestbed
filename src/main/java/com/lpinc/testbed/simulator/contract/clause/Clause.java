package com.lpinc.testbed.simulator.contract.clause;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.monitor.Monitor;
import com.lpinc.testbed.simulator.utils.Flag;

public abstract class Clause<C extends Contract<?, ?, ?>> {

  private final Flag flag;
  private final C contract;

  public Clause(Flag flag, C contract) {
    this.flag = flag;
    this.contract = contract;
  }

  public Flag getFlag() {
    return flag;
  }

  public C getContract() {
    return contract;
  }

  public abstract Agent getBindingAgent();

  public abstract void action(Monitor monitor);
}

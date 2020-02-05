package com.lpinc.testbed.simulator.contract.clause;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.contract.RentContract;
import com.lpinc.testbed.simulator.monitor.Monitor;
import com.lpinc.testbed.simulator.utils.Flag;

public class MaintenanceClause extends Clause<RentContract> {

  public MaintenanceClause(RentContract contract) {
    super(Flag.MAINTENANCE, contract);
  }

  @Override
  public Agent getBindingAgent() {
    return getContract().getProvider();
  }

  @Override
  public void action(Monitor monitor) {
  }
}

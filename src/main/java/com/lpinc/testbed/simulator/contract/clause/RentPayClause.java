package com.lpinc.testbed.simulator.contract.clause;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.contract.RentContract;
import com.lpinc.testbed.simulator.monitor.Monitor;
import com.lpinc.testbed.simulator.utils.Flag;

public class RentPayClause extends Clause<RentContract> {

  public RentPayClause(RentContract contract) {
    super(Flag.RENT_PAYMENT, contract);
  }

  @Override
  public Agent getBindingAgent() {
    return getContract().getCustomer();
  }

  @Override
  public void action(Monitor monitor) {
    monitor.getCustomerData().set(0,
        monitor.getCustomerData().get(0) - monitor.getResourceData().get(0));
  }
}

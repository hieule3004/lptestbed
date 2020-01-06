package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.principal.Landlord;
import com.lpinc.testbed.simulator.resource.Property;
import com.lpinc.testbed.simulator.utils.ExitCode;

public class RentPayClause implements Clause<Landlord, Tenant, Property> {

  private Contract<Landlord, Tenant, Property> contract;

  public RentPayClause(
      Contract<Landlord, Tenant, Property> contract) {
    this.contract = contract;
  }

  @Override
  public Contract<Landlord, Tenant, Property> getContract() {
    return contract;
  }

  @Override
  public ExitCode inspect() {
    return contract.getConsumer().response(this);
  }
}

package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.contract.clause.MaintenanceClause;
import com.lpinc.testbed.simulator.contract.clause.RentPayClause;
import com.lpinc.testbed.simulator.agent.customer.Tenant;
import com.lpinc.testbed.simulator.agent.provider.Landlord;
import com.lpinc.testbed.simulator.resource.Property;

public class RentContract extends Contract<Landlord, Tenant, Property> {

  public RentContract(Tenant tenant, Property property, int duration) {
    super(property.getOwner(), tenant, property, duration);
    //clause
    getClauses().add(new RentPayClause(this));
    getClauses().add(new MaintenanceClause(this));
  }
}

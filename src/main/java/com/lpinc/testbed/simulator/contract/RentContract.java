package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.principal.Landlord;
import com.lpinc.testbed.simulator.resource.Property;
import java.util.ArrayList;
import java.util.List;

public class RentContract extends Contract<Landlord, Tenant, Property> {

  private static final List<Clause<Landlord, Tenant, Property>> clauses = new ArrayList<>();

  public RentContract(Tenant tenant, Property property) {
    super(property.getOwner(), tenant, property, clauses);
    //clause
    clauses.add(new RentPayClause(this));
  }
}

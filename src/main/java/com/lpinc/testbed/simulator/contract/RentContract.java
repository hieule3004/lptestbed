package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.event.request.MaintenanceRequest;
import com.lpinc.testbed.simulator.event.request.Request;
import com.lpinc.testbed.simulator.event.request.RentPaymentRequest;
import com.lpinc.testbed.simulator.resource.Property;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class RentContract extends Contract {

  public RentContract(Tenant tenant, Property property, int months) {
    super();
    LocalDate date = LocalDate.now();
    //rent payment clause
    Clause rpc = new Clause(this, tenant, property);
    List<Request> rpo = new ArrayList<>();
    for (int i = 0; i < months; i++) {
      rpo.add(new RentPaymentRequest(date.plusMonths(i), rpc));
    }
    addObligations(rpc, rpo);
    //maintenance clause
    Clause mc = new Clause(this, property.getOwner(), property);
    List<Request> mo = new ArrayList<>();
    mo.add(new MaintenanceRequest(date.plusMonths(months / 2), mc));
    addObligations(mc, mo);
  }
}

package com.lpinc.testbed.simulator.event;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.resource.Land;
import com.lpinc.testbed.simulator.utils.ExitCode;

public class RentPaymentEvent implements Event {

  private final Tenant tenant;
  private final Land land;

  private ExitCode result;

  public RentPaymentEvent(Tenant tenant, Land land) {
    this.tenant = tenant;
    this.land = land;
  }

  public double getRent() {
    return land.getRent();
  }

  @Override
  public ExitCode process() {
    result = tenant.response(this);
    if (result == ExitCode.ERROR) throw new UnsupportedOperationException("Error occurred");
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s monthly rent of %.2f, balance is now %s",
        tenant, result == ExitCode.SUCCESS ? "paid" : "didn't pay", land.getOwner(),
        land.getRent(), tenant.getCurrentBalance());
  }
}

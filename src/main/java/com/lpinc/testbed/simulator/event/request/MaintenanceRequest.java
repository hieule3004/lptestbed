package com.lpinc.testbed.simulator.event.request;

import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.event.tag.EstateRequestType;
import com.lpinc.testbed.simulator.runner.Simulator;
import java.time.LocalDate;

public final class MaintenanceRequest extends Request {

  public MaintenanceRequest(LocalDate date, Clause clause) {
    super(date, EstateRequestType.MAINTENANCE, clause);
  }

  @Override
  public void action(Simulator simulator, Response<?> response, int branch) {
  }
}

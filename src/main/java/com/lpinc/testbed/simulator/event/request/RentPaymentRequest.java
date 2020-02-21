package com.lpinc.testbed.simulator.event.request;

import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.event.tag.EstateRequestType;
import com.lpinc.testbed.simulator.runner.Simulator;
import java.time.LocalDate;

public final class RentPaymentRequest extends Request {

  public RentPaymentRequest(LocalDate date, Clause clause) {
    super(date, EstateRequestType.RENT_PAYMENT, clause);
  }

  @Override
  public void action(Simulator simulator, Response<?> response, int branch) {
    simulator.getDataOf(getClause().getAgent()).update(branch, 0,
        v -> v - simulator.getDataOf(getClause().getResource()).get(branch,0));
  }
}

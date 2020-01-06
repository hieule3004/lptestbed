package com.lpinc.testbed.simulator.event;

import com.lpinc.testbed.simulator.contract.RentContract;
import com.lpinc.testbed.simulator.utils.ExitCode;

public class RentPaymentEvent implements Event<RentContract> {

  @Override
  public ExitCode process(RentContract contract) {
    return ExitCode.safe(contract.getClauses().get(0).inspect());
  }
}

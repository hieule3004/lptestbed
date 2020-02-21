package com.lpinc.testbed.simulator.event.response;

public final class BooleanResponse extends Response<Boolean> {

  public BooleanResponse(Boolean response) {
    super(response);
  }

  @Override
  public double evaluate() {
    return Boolean.compare(getResponse(), false);
  }
}

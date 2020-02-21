package com.lpinc.testbed.simulator.event.response;

public final class NullResponse extends Response<Object> {

  public NullResponse() {
    super(null);
  }

  @Override
  public double evaluate() {
    return 0;
  }
}

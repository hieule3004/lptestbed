package com.lpinc.testbed.simulator.event.response;

import com.lpinc.testbed.simulator.event.Event;
import java.time.LocalDate;

public abstract class Response<T> extends Event {

  private T response;

  public Response(T response) {
    super(LocalDate.now());
    this.response = response;
  }

  public final boolean ok() {
    return response != null;
  }

  public final T getResponse() {
    return response;
  }

  public abstract double evaluate();
}

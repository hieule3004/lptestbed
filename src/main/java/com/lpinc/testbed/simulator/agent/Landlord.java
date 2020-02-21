package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.agent.provider.Provider;
import com.lpinc.testbed.simulator.event.request.Request;
import com.lpinc.testbed.simulator.event.response.BooleanResponse;
import com.lpinc.testbed.simulator.event.response.NullResponse;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.event.tag.EstateRequestType;

public final class Landlord implements Provider {

  private static int idCount = 0;

  private final String name;

  public Landlord() {
    name = String.valueOf(idCount++);
  }

  @Override
  public Double[] getData() {
    return null;
  }

  @Override
  public Response<?> reply(Request request) {
    if (request.getType() == EstateRequestType.MAINTENANCE) {
      return new BooleanResponse(true);
    }
    return new NullResponse();
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

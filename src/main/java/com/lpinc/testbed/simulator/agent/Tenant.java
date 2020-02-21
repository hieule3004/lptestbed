package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.agent.customer.Customer;
import com.lpinc.testbed.simulator.event.request.Request;
import com.lpinc.testbed.simulator.event.response.BooleanResponse;
import com.lpinc.testbed.simulator.event.response.NullResponse;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.event.tag.EstateRequestType;
import java.util.Random;

public final class Tenant implements Customer {

  private static int idCount = 0;

  private final Double[] data;
  private final String name;
  private final double honesty;
  private final Random random;

  public Tenant(double balance) {
    this.data = new Double[]{balance};
    name = String.valueOf(idCount++);
    honesty = 0.8;
//    honesty = random.nextDouble();
    random = new Random();
  }

  @Override
  public Double[] getData() {
    return data;
  }

  @Override
  public Response<?> reply(Request request) {
    if (random.nextDouble() > honesty) {
      return new NullResponse();
    } else if (request.getType() == EstateRequestType.RENT_PAYMENT) {
      return new BooleanResponse(true);
    }
    return new NullResponse();
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

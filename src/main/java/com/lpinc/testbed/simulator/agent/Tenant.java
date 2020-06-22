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

  private final Random random = new Random();
  private final Double[] data;
  private final String name;
  private final double honesty;
  private double judgement = 2 * random.nextDouble() - 1;
  private int ratingCount = 0;

  public Tenant(double balance) {
    this.data = new Double[]{balance};
    name = String.valueOf(idCount++);
    honesty = 0.8;
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

  @Override
  public double getJudgement() {
    return judgement;
  }

  @Override
  public void updateJudgement(double offset) {
    judgement = (judgement * ratingCount + offset) / (ratingCount + 1);
    ratingCount++;
  }

  @Override
  public int getRatingCount() {
    return ratingCount;
  }

  @Override
  public void updateRatingCount(int number) {
    ratingCount -= number;
  }
}

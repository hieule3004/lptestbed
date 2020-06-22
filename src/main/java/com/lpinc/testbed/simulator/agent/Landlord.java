package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.agent.provider.Provider;
import com.lpinc.testbed.simulator.event.request.Request;
import com.lpinc.testbed.simulator.event.response.BooleanResponse;
import com.lpinc.testbed.simulator.event.response.NullResponse;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.event.tag.EstateRequestType;
import java.util.Random;

public final class Landlord implements Provider {

  private static int idCount = 0;

  private final String name;
  private Random random = new Random();
  private double judgement = 2 * random.nextDouble() - 1;
  private int ratingCount = 0;
  private final double honesty;

  public Landlord() {
    name = String.valueOf(idCount++);
    honesty = 0.9;
  }

  @Override
  public Double[] getData() {
    return null;
  }

  @Override
  public Response<?> reply(Request request) {
    if (random.nextDouble() > honesty) {
      return new NullResponse();
    } else if (request.getType() == EstateRequestType.MAINTENANCE) {
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

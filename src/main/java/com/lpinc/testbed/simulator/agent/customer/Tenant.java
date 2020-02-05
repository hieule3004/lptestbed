package com.lpinc.testbed.simulator.agent.customer;

import com.lpinc.testbed.simulator.contract.clause.Clause;
import com.lpinc.testbed.simulator.utils.Flag;
import java.util.Random;

public class Tenant implements Customer {

  private static int idCount = 0;

  private final Double[] data;
  private final String name;
  private final double honesty;
  private Random random;

  public Tenant(double balance) {
    this.data = new Double[] {balance};
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
  public boolean response(Clause<?> clause) {
    return random.nextDouble() < honesty
        && clause.getFlag() == Flag.RENT_PAYMENT;
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.utils.Renewable;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.utils.ExitCode;

import java.util.Random;

public class Tenant implements Agent {

  private static int idCount = 0;

  private final Renewable<Double> balance;

  private final String name;
  private final double honesty;
  private Random random;

  public Tenant(double balance, int period) {
    this.balance = new Renewable<>(balance, period);
    name = String.valueOf(idCount++);
    honesty = 0.8;//random.nextDouble();
    random = new Random();
  }

  public double getCurrentBalance() {
    return balance.get();
  }

  @Override
  public ExitCode request(Event event) {
    return ExitCode.ERROR;
  }

  @Override
  public ExitCode response(Event event) {
    if (random.nextDouble() > honesty) {
      return ExitCode.FAILURE;
    }
    if (event instanceof RentPaymentEvent) {
      double rent = ((RentPaymentEvent) event).getLand().getRent();
      if (rent <= getCurrentBalance()) {
        balance.set(balance.get() - rent);
        return ExitCode.SUCCESS;
      } else {
        return ExitCode.FAILURE;
      }
    }
    return ExitCode.ERROR;
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.utils.ExitCode;

import java.util.Random;

public class Tenant implements Agent {

  private static int idCount = 0;

  private final double balance;
  private final int period;

  private final String name;
  private final double honesty;
  private double currentBalance;
  private int countdown;
  private Random random;

  public Tenant(double balance, int period) {
    this.balance = balance;
    this.period = period;
    name = String.valueOf(idCount++);
    honesty = 0.8;//random.nextDouble();
    currentBalance = balance;
    countdown = period;
    random = new Random();
  }

  public double getCurrentBalance() {
    return currentBalance;
  }

  @Override
  public ExitCode request(Event event) {
    return ExitCode.ERROR;
  }

  @Override
  public ExitCode response(Event event) {
    countdown--;
    if (countdown == 0) {
      currentBalance = balance;
      countdown = period;
    }

      if (random.nextDouble() > honesty) {
          return ExitCode.FAILURE;
      }

    if (event instanceof RentPaymentEvent) {
      double rent = ((RentPaymentEvent) event).getRent();
      if (rent <= currentBalance) {
        currentBalance -= rent;
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

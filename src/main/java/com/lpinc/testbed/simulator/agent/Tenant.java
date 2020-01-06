package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.contract.RentPayClause;
import com.lpinc.testbed.simulator.utils.ExitCode;
import com.lpinc.testbed.simulator.utils.Renewable;
import java.util.Random;

public class Tenant implements Consumer {

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

  @Override
  public ExitCode response(Clause<?, ?, ?> clause) {
    if (random.nextDouble() > honesty) {
      return ExitCode.FAILURE;
    }
    if (clause instanceof RentPayClause) {
      double rent = ((RentPayClause) clause).getContract().getResource().getRent();
      if (rent <= balance.get()) {
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

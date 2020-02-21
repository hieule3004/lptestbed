package com.lpinc.testbed.simulator;

import com.lpinc.testbed.simulator.runner.EstateRunner;
import com.lpinc.testbed.simulator.utils.Repeat;

public class Main {

  public static void main(String[] args) {
    runSampleSimulator();
    runSingularSimulator(1.0, 50.0, 12);
  }

  public static void runSampleSimulator() {
    Double[][] landPrices = new Double[5][4];
    Repeat.fill(landPrices, i -> 2.0);
    Double[] tenantValues = new Double[20];
    Repeat.parallel(20, i -> tenantValues[i] = 50.0);
    Integer[][] periods = new Integer[20][20];
    Repeat.fill(periods, i -> 12);

    EstateRunner simulator = new EstateRunner(landPrices, tenantValues, periods);
    simulator.run();
  }

  public static void runSingularSimulator(double rent, double balance, int period) {
    Double[][] landPrices = new Double[][]{{rent}};
    Double[] tenantValues = new Double[]{balance};
    Integer[][] periods = new Integer[][]{{period}};

    EstateRunner simulator = new EstateRunner(landPrices, tenantValues, periods);
    simulator.run();
  }
}

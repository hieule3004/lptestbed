package com.lpinc.testbed.simulator.utils;

public class Domain {

  public static final double squeeze(double low, double high, double value) {
    return Math.max(low, Math.min(high, value));
  }

}

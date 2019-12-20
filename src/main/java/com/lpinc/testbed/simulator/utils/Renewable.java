package com.lpinc.testbed.simulator.utils;

public class Renewable<V> {

  private final V initialValue;
  private final int period;

  private V currentValue;
  private int counter;

  public Renewable(V initialValue, int period) {
    this.period = period;
    this.initialValue = initialValue;
  }

  public V get() {
    checkAndCount(false);
    return currentValue;
  }

  public void set(V newValue) {
    checkAndCount(true);
    currentValue = newValue;
  }

  private void checkAndCount(boolean isSet) {
    if (counter <= 0) {
      currentValue = initialValue;
      counter = period;
    }
    if (isSet) {
      counter--;
    }
  }
}

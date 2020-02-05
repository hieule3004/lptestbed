package com.lpinc.testbed.simulator.utils;

import java.util.Arrays;

public class Renewable<V> {

  private final V[] initialValue;
  private final int period;

  private V[] currentValue;
  private int counter;

  public Renewable(V[] initialValue, int period) {
    this.period = period;
    this.initialValue = initialValue;
  }

  public V get(int index) {
    checkAndCount(false);
    return currentValue[index];
  }

  public void set(int index, V newValue) {
    checkAndCount(true);
    currentValue[index] = newValue;
  }

  private void checkAndCount(boolean isSet) {
    if (counter <= 0) {
      currentValue = Arrays.copyOf(initialValue, initialValue.length);
      counter = period;
    }
    if (isSet) {
      counter--;
    }
  }
}

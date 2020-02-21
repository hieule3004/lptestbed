package com.lpinc.testbed.simulator.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class Splitter<V> {

  private final V[] initialValue;
  private final int period;
  private Map<Integer, V[]> tree;

  public Splitter(V[] initialValue, int period) {
    this.initialValue = initialValue;
    this.period = period;
    this.tree = new HashMap<>();
  }

  public V get(int branch, int index) {
    return choose(branch)[index];
  }

  public void update(int branch, int index, UnaryOperator<V> op) {
    V[] currentValue = choose(branch);
    currentValue[index] = op.apply(currentValue[index]);
  }

  private V[] choose(int branch) {
    if (period <= 0) {
      tree.remove(branch);
    }
    if (!tree.containsKey(branch)) {
      tree.put(branch, Arrays.copyOf(initialValue, initialValue.length));
    }
    return tree.get(branch);
  }
}

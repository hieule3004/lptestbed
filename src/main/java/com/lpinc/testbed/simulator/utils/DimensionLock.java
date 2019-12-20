package com.lpinc.testbed.simulator.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DimensionLock {

  private final Lock[][] locks;

  public DimensionLock(int... dims) {
    this.locks = new Lock[dims.length][];
    Repeat.parallel(dims.length, i -> locks[i] = new Lock[dims[i]]);
    Repeat.fill(locks, i -> new ReentrantLock());
  }

  public void lock(int... dims) {
    for (int i = 0; i < dims.length; i++) {
      if (dims[i] >= 0 && dims[i] < locks[i].length) {
        locks[i][dims[i]].lock();
      }
    }
  }

  public void unlock(int... dims) {
    for (int i = dims.length - 1; i >= 0; i--) {
      if (dims[i] >= 0 && dims[i] < locks[i].length) {
        locks[i][dims[i]].unlock();
      }
    }
  }
}

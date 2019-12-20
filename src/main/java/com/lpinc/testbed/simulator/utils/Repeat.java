package com.lpinc.testbed.simulator.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Repeat {

  public static void parallel(int length, Consumer<Integer> function) {
    IntStream.range(0, length).parallel().forEach(function::accept);
  }

  public static void fill(Object[] array, Function<Integer, Object> function) {
    parallel(array.length, i -> {
      if (array.getClass().getComponentType().isArray()) {
        fill((Object[]) array[i], function);
      } else {
        array[i] = function.apply(i);
      }
    });
  }
}

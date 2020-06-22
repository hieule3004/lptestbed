package com.lpinc.testbed.simulator.knn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class KNNEvaluator {

  private double[][] data;
  private final double[] values;
  private double[] point;

  public KNNEvaluator(double[][] data, double[] values, double[] point) {
    this.data = data;
    this.values = values;
    this.point = point;
  }

  private double distance(double[] point1, double[] point2) {
    if (point1.length != point2.length) {
      throw new RuntimeException("cannot compare arrays of different length");
    }
    return Math.sqrt(IntStream.range(0, point1.length)
        .mapToDouble(i -> Math.pow(point1[i] - point2[i], 2))
        .reduce(0, Double::sum));
  }

  public double predict() {
    int k = (int) Math.round(Math.sqrt(data.length)) / 2;
    double[][] sorted = Arrays.stream(data)
        .sorted(Comparator.comparing(o -> distance(point, (double[]) o)).reversed())
        .limit(k).toArray(double[][]::new);
    double weightSum = 0;
    double sum = 0;
    for (int i = 0; i < k; i++) {
      double weight = 1 / distance(sorted[i], point);
      sum += values[i] * weight;
      weightSum += weight;
    }
    return sum / weightSum;
  }
}

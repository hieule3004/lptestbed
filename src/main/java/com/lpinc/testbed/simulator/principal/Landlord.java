package com.lpinc.testbed.simulator.principal;

import com.lpinc.testbed.simulator.resource.Land;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Landlord implements Principal<Landlord> {

  private static int idCount = 0;

  private final String name;
  private final List<Land> resources;

  public Landlord(Double[] rents) {
    name = String.valueOf(idCount++);
    resources = Arrays.stream(rents).map(r -> new Land(this, r)).collect(Collectors.toList());
  }

  @Override
  public List<Land> getResources() {
    return resources;
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

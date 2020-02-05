package com.lpinc.testbed.simulator.agent.provider;

import com.lpinc.testbed.simulator.contract.clause.Clause;
import com.lpinc.testbed.simulator.resource.Property;
import com.lpinc.testbed.simulator.utils.Flag;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Landlord implements Provider {

  private static int idCount = 0;

  private final String name;
  private final List<Property> resources;

  public Landlord(Double[] rents) {
    name = String.valueOf(idCount++);
    resources = Arrays.stream(rents).map(r -> new Property(this, r)).collect(Collectors.toList());
  }

  @Override
  public Double[] getData() {
    return null;
  }

  @Override
  public List<Property> getResources() {
    return resources;
  }

  @Override
  public boolean response(Clause<?> clause) {
    return clause.getFlag() == Flag.MAINTENANCE;
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

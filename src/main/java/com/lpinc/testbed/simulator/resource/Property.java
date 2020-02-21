package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.agent.Landlord;

public final class Property implements Resource {

  private static int idCount = 0;

  private final Landlord owner;
  private final Double[] data;
  private final String name;

  public Property(Landlord owner, double rent) {
    this.owner = owner;
    this.data = new Double[] {rent};
    name = String.valueOf(idCount++);
  }

  @Override
  public Double[] getData() {
    return data;
  }

  @Override
  public Landlord getOwner() {
    return owner;
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), name);
  }
}

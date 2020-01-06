package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.principal.Landlord;

public class Property implements Resource<Landlord> {

  private static int idCount = 0;

  private final Landlord owner;
  private double rent;
  private final String name;

  public Property(Landlord owner, double rent) {
    this.owner = owner;
    this.rent = rent;
    name = String.valueOf(idCount++);
  }

  public double getRent() {
    return rent;
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

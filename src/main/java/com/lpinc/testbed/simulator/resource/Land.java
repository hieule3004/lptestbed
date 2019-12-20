package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.principal.Landlord;

public class Land implements Resource<Landlord> {

    private static int idCount = 0;

    private final int ID;
    private final Landlord owner;
    private double rent;

    public Land(Landlord owner, double rent) {
        this.ID = idCount++;
        this.owner = owner;
        this.rent = rent;
    }

    public double getRent() {
        return rent;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Landlord getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.join(" ", getClass().getSimpleName(), String.valueOf(ID));
    }
}

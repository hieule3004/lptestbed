package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.agent.Agent;

public class Land extends Resource {

    private static int idCount = 0;

    private final int ID;
    private final Agent owner;
    private double rent;

    public Land(Agent owner, double rent) {
        this.ID = idCount++;
        this.owner = owner;
        this.rent = rent;
    }

    public int getID() {
        return ID;
    }

    @Override
    public Agent getOwner() {
        return owner;
    }

    public double getRent() {
        return rent;
    }

    @Override
    public String toString() {
        return String.format("Land %s[%s]", ID, rent);
    }
}

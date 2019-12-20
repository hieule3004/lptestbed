package com.lpinc.testbed.simulator.principal;

import com.lpinc.testbed.simulator.resource.Land;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Landlord implements Principal<Landlord> {

    private static int idCount = 0;

    private final int ID;

    private List<Land> resources;
    private double repWeight;

    public Landlord(Double[] landPrices, double repWeight) {
        this.ID = idCount++;
        this.repWeight = repWeight;
        //init
        this.resources = new ArrayList<>();
        Arrays.stream(landPrices).forEach(p -> resources.add(new Land(this, p)));
    }

    @Override
    public List<Land> getResources() {
        return resources;
    }

    @Override
    public double getRepWeight() {
        return repWeight;
    }

    @Override
    public String toString() {
        return String.join(" ", getClass().getSimpleName(), String.valueOf(ID));
    }
}

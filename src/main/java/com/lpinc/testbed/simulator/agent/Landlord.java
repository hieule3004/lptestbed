package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.ExitCode;
import com.lpinc.testbed.simulator.resource.Land;
import com.lpinc.testbed.simulator.resource.Resource;

import java.util.Arrays;

public class Landlord extends Agent {

    private static int idCount = 0;

    private final int ID;
    private double repWeight;

    public Landlord(double[] landPrices, double repWeight) {
        super(Role.LANDLORD);
        this.repWeight = repWeight;
        this.ID = idCount++;
        Arrays.stream(landPrices).forEach(p -> resources.add(new Land(this, p)));
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public double getRepWeight() {
        return repWeight;
    }

    @Override
    public int request(Event<? extends Agent, ? extends Resource> event) {
        return ExitCode.ERROR;
    }

    @Override
    public int response(Event<? extends Agent, ? extends Resource> event) {
        return ExitCode.ERROR;
    }
}


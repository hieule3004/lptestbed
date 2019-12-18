package com.lpinc.testbed.simulator.event;

import com.lpinc.testbed.simulator.Simulator;
import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.resource.Land;

public class RentPaymentEvent extends Event<Tenant, Land> {

    private Tenant tenant;
    private Land land;

    public RentPaymentEvent(Tenant tenant, Land land) {
        this.tenant = tenant;
        this.land = land;
    }

    public double getRent() {
        return land.getRent();
    }

    @Override
    public void process(Simulator<Tenant, Land> simulator) {
        int response = tenant.response(this);
        setResult(response == ExitCode.SUCCESS);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s monthly rent of %s",
                tenant, getResult() ? "paid" : "didn't pay", land.getOwner(), land);
    }
}

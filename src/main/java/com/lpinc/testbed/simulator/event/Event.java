package com.lpinc.testbed.simulator.event;

import com.lpinc.testbed.simulator.Simulator;
import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.resource.Resource;

public abstract class Event<A extends Agent, R extends Resource> {

    private boolean result;

    public boolean getResult() {
        return result;
    }

    protected void setResult(boolean result) {
        this.result = result;
    }

    public abstract void process(Simulator<A, R> simulator);
}

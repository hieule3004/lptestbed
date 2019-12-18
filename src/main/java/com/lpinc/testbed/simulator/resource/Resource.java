package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.agent.Agent;

public abstract class Resource {

    public abstract int getID();

    public abstract Agent getOwner();
}

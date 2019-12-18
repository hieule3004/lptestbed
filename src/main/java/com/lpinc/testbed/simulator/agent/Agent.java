package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.resource.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Agent {

    private final Role role;
    protected List<Resource> resources = new ArrayList<>();

    Agent(Role role) {
        this.role = role;
    }

    public abstract int getID();

    public abstract double getRepWeight();

    public List<Resource> getResources() {
        return resources;
    }

    //action methods
    public abstract int request(Event<? extends Agent, ? extends Resource> event);
    public abstract int response(Event<? extends Agent, ? extends Resource> event);

    @Override
    public String toString() {
        return String.format("%s %s{%s}", role.getFormat(), getID(), Arrays.toString(resources.toArray()));
    }
}

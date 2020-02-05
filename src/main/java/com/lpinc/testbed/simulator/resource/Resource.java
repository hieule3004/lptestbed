package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.agent.Agent;

public interface Resource<A extends Agent> {

  Double[] getData();

  A getOwner();
}

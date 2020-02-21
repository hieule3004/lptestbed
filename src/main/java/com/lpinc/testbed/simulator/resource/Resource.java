package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.utils.Data;

public interface Resource extends Data {

  Agent getOwner();
}

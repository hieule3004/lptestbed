package com.lpinc.testbed.simulator.principal;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.resource.Resource;
import java.util.List;

public interface Provider extends Agent {

  List<? extends Resource<?>> getResources();
}

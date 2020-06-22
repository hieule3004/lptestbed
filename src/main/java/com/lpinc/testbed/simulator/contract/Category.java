package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.agent.Landlord;
import com.lpinc.testbed.simulator.agent.Tenant;
import java.util.HashMap;
import java.util.Map;

public enum Category {
  ESTATE(2),
  TASK(1);

  private static final Map<Class<? extends Agent>, Category> tag = new HashMap<>() {{
     put(Landlord.class, ESTATE);
     put(Tenant.class, ESTATE);
  }};

  private final int frequency;

  Category(int frequency) {
    this.frequency = frequency;
  }

  public static int getFrequency(Class<? extends Agent> cl) {
    return tag.get(cl).frequency;
  }
}

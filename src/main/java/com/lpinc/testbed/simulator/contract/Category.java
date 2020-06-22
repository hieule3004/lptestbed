package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.agent.Landlord;
import com.lpinc.testbed.simulator.agent.Tenant;
import java.util.HashMap;
import java.util.Map;

public enum Category {
  ESTATE,
  TASK;

  private static final Map<Class<? extends Agent>, Category> tag = new HashMap<>() {{
     put(Landlord.class, ESTATE);
     put(Tenant.class, ESTATE);
  }};

  private static final Map<Category, Integer> frequency = new HashMap<>() {{
    put(ESTATE, 2);
    put(TASK, 1);
  }};

  public static int getFrequency(Class<? extends Agent> cl) {
    return frequency.get(tag.get(cl));
  }

  public void updateFrequency(Category category, int frequency) {
    Category.frequency.put(category, frequency);
  }
}

package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.contract.clause.Clause;

public interface Agent {

  Double[] getData();

  boolean response(Clause<?> clause);
}

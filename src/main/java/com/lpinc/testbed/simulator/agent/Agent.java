package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.utils.ExitCode;

public interface Agent {

  ExitCode response(Clause<?, ?, ?> clause);
}

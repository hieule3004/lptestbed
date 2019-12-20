package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.utils.ExitCode;

public interface Agent {

  ExitCode request(Event event);

  ExitCode response(Event event);
}

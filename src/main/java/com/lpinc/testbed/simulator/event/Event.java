package com.lpinc.testbed.simulator.event;

import com.lpinc.testbed.simulator.utils.ExitCode;

public interface Event {

  ExitCode process();
}

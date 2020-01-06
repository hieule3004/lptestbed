package com.lpinc.testbed.simulator.event;

import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.utils.ExitCode;

public interface Event<C extends Contract<?, ?, ?>> {

  ExitCode process(C contract);
}

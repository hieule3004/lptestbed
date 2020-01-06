package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Consumer;
import com.lpinc.testbed.simulator.principal.Provider;
import com.lpinc.testbed.simulator.resource.Resource;
import com.lpinc.testbed.simulator.utils.ExitCode;

public interface Clause<P extends Provider, C extends Consumer, R extends  Resource<P>> {

  Contract<P, C, R> getContract();

  ExitCode inspect();
}

package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.resource.Resource;

public final class Clause {

  private final Contract contract;
  private final Agent agent;
  private final Resource resource;

  public Clause(Contract contract, Agent agent, Resource resource) {
    this.contract = contract;
    this.agent = agent;
    this.resource = resource;
  }

  public final Contract getContract() {
    return contract;
  }

  public final Agent getAgent() {
    return agent;
  }

  public final Resource getResource() {
    return resource;
  }
}

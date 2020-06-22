package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.resource.Resource;

public final class Clause {

  private final Contract contract;
  private int agentIndex;
  private final Resource resource;

  public Clause(Contract contract, Resource resource, int agentIndex) {
    this.contract = contract;
    this.agentIndex = agentIndex;
    this.resource = resource;
  }

  public final Contract getContract() {
    return contract;
  }

  public final Agent getAgent() {
    return contract.getAgents().get(agentIndex);
  }

  public final Agent getOtherAgent() {
    return contract.getAgents().get((agentIndex + 1) % 2);
  }

  public final Resource getResource() {
    return resource;
  }
}

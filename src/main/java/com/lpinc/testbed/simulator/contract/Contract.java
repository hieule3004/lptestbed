package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Consumer;
import com.lpinc.testbed.simulator.principal.Provider;
import com.lpinc.testbed.simulator.resource.Resource;
import java.util.List;

public abstract class Contract<P extends Provider, C extends Consumer, R extends Resource<P>> {

  private final P provider;
  private final C consumer;
  private final R resource;
  private final List<Clause<P, C, R>> clauses;

  public Contract(P provider, C consumer, R resource, List<Clause<P, C, R>> clauses) {
    this.provider = provider;
    this.consumer = consumer;
    this.resource = resource;
    this.clauses = clauses;
  }

  public P getProvider() {
    return provider;
  }

  public C getConsumer() {
    return consumer;
  }

  public R getResource() {
    return resource;
  }

  public List<Clause<P, C, R>> getClauses() {
    return clauses;
  }
}

package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.contract.clause.Clause;
import com.lpinc.testbed.simulator.agent.customer.Customer;
import com.lpinc.testbed.simulator.agent.provider.Provider;
import com.lpinc.testbed.simulator.resource.Resource;
import java.util.ArrayList;
import java.util.List;

public abstract class Contract<P extends Provider, C extends Customer, R extends Resource<P>> {

  private final P provider;
  private final C customer;
  private final R resource;
  private final int duration;
  private final List<Clause<? extends Contract<P, C, R>>> clauses;

  public Contract(P provider, C customer, R resource, int duration) {
    this.provider = provider;
    this.customer = customer;
    this.resource = resource;
    this.duration = duration;
    this.clauses = new ArrayList<>();
  }

  public P getProvider() {
    return provider;
  }

  public C getCustomer() {
    return customer;
  }

  public R getResource() {
    return resource;
  }

  public int getDuration() {
    return duration;
  }

  protected List<Clause<? extends Contract<P, C, R>>> getClauses() {
    return clauses;
  }

  public Clause<? extends Contract<P, C, R>> getClause(int index) {
    return clauses.get(index);
  }

  public int size() {
    return clauses.size();
  }

  @Override
  public String toString() {
    return String.join(" ", getClass().getSimpleName(), customer.toString(), resource.toString());
  }
}

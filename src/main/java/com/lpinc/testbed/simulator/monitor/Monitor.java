package com.lpinc.testbed.simulator.monitor;

import com.lpinc.testbed.simulator.contract.clause.Clause;
import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.utils.Renewable;
import com.lpinc.testbed.simulator.utils.Repeat;

public final class Monitor {

  private Contract<?, ?, ?> contract;
  private Double[] valueVector;
  private int[] counterVector;

//  private Renewable<Double> providerData;
  private Renewable<Double> customerData;
  private Renewable<Double> resourceData;

  private boolean running = true;

  public Monitor(Contract<?, ?, ?> contract, Double[] valueVector) {
    this.contract = contract;
    this.valueVector = valueVector;
    //init
    this.counterVector = new int[valueVector.length];
//    this.providerData = new Renewable<>(contract.getProvider().getData(), contract.getDuration());
    this.customerData = new Renewable<>(contract.getCustomer().getData(), contract.getDuration());
    this.resourceData = new Renewable<>(contract.getResource().getData(), contract.getDuration());
  }

  public Monitor(Contract<?, ?, ?> contract) {
    this(contract, new Double[contract.size()]);
    Repeat.fill(valueVector, i -> 0.0);
  }

  public Double[] getValueVector() {
    return valueVector;
  }

  public void trigger(int index) {
    Clause<?> clause = contract.getClause(index);
    boolean response = clause.getBindingAgent().response(clause);
    if (response) {
      clause.action(this);
      valueVector[index]++;
    }
    counterVector[index]++;
  }

  public void average() {
    if (running) {
      Repeat.fill(valueVector, i -> valueVector[i] / counterVector[i]);
      running = false;
    }
  }

  public Contract<?, ?, ?> getContract() {
    return contract;
  }

//  public Renewable<Double> getProviderData() {
//    return providerData;
//  }

  public Renewable<Double> getCustomerData() {
    return customerData;
  }

  public Renewable<Double> getResourceData() {
    return resourceData;
  }
}

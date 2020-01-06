package com.lpinc.testbed.simulator;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.contract.RentContract;
import com.lpinc.testbed.simulator.principal.Landlord;
import com.lpinc.testbed.simulator.resource.Property;
import com.lpinc.testbed.simulator.simulator.EstateSimulator;
import com.lpinc.testbed.simulator.utils.Repeat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    runSampleSimulator();
    runSingularSimulator(1.0, 50.0, 12);
  }

  public static void runSampleSimulator() {
    Double[][] landPrices = new Double[5][4];
    Repeat.fill(landPrices, i -> 2.00);
    int[][] tenantValues = new int[20][2];
    Repeat.parallel(20, i -> tenantValues[i] = new int[]{1000, 12});

    List<Property> resources = Arrays.stream(landPrices).flatMap(price -> {
      Landlord landlord = new Landlord(price);
      return landlord.getResources().stream();
    }).collect(Collectors.toList());
    List<Tenant> tenants = Arrays.stream(tenantValues)
        .map(value -> new Tenant(value[0], value[1]))
        .collect(Collectors.toList());
    RentContract[][] contracts = new RentContract[tenants.size()][resources.size()];
    for (int i = 0; i < tenants.size(); i++) {
      for (int j = 0; j < resources.size(); j++) {
        contracts[i][j] = new RentContract(tenants.get(i), resources.get(j));
      }
    }

    EstateSimulator simulator = new EstateSimulator(tenants, resources, contracts);
    simulator.run();
  }

  public static void runSingularSimulator(double rent, double balance, int period) {
    List<Tenant> tenants = new ArrayList<>();
    Tenant tenant = new Tenant(balance, period);
    tenants.add(tenant);
    Landlord landlord = new Landlord(new Double[] {rent});
    List<Property> properties = new ArrayList<>(landlord.getResources());
    RentContract[][] contracts = new RentContract[][] {
        new RentContract[] { new RentContract(tenant, properties.get(0)) }
    };

    EstateSimulator simulator = new EstateSimulator(tenants, properties, contracts);
    simulator.run();
  }
}

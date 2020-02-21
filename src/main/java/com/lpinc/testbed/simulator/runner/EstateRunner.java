package com.lpinc.testbed.simulator.runner;

import com.lpinc.testbed.simulator.agent.Landlord;
import com.lpinc.testbed.simulator.contract.RentContract;
import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.resource.Property;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EstateRunner {

  ParallelRunner runner;

  public EstateRunner(Double[][] rents, Double[] balances, Integer[][] periods) {
    this.runner = new ParallelRunner(initiate(rents, balances, periods));
  }

  private static Simulator[][] initiate(Double[][] rents, Double[] balances, Integer[][] periods) {
    List<Property> properties = Arrays.stream(rents).flatMap(rent -> {
      Landlord landlord = new Landlord();
      return Arrays.stream(rent).map(r -> new Property(landlord, r));
    }).collect(Collectors.toList());
    List<Tenant> tenants = Arrays.stream(balances)
        .map(Tenant::new)
        .collect(Collectors.toList());
    Simulator[][] simulators = new Simulator[tenants.size()][properties.size()];
    for (int i = 0; i < tenants.size(); i++) {
      for (int j = 0; j < properties.size(); j++) {
        RentContract contract = new RentContract(tenants.get(i), properties.get(j), periods[i][j]);
        simulators[i][j] = new Simulator(contract);
      }
    }
    return simulators;
  }

  public void print(double[][][] matrix) {
    AtomicInteger length = new AtomicInteger();
    String block = Arrays.stream(matrix).map(row ->
        Arrays.stream(row).map(arr ->
            Arrays.stream(arr)
                .mapToObj(d -> String.format("%s%.2f", d < 0 ? "-" : " ", Math.abs(d)))
                .collect(Collectors.toList()).toString()
        ).collect(Collectors.joining(" "))
    ).peek(r -> length.set(r.length())).collect(Collectors.joining("|\n|"));
    String dash = "=".repeat(length.get() + 2);
    String string = String.format("%s\n|%s|\n%s", dash, block, dash);
    System.out.println(string);
  }

  public void run() {
    runner.run();
    print(runner.getCalcTable());
  }

}

package com.lpinc.testbed.simulator.simulator;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.contract.Contract;
import com.lpinc.testbed.simulator.contract.RentContract;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.principal.Landlord;
import com.lpinc.testbed.simulator.resource.Property;
import com.lpinc.testbed.simulator.utils.ExitCode;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EstateSimulator extends Simulator<Landlord, Tenant, Property> {

  public EstateSimulator(List<Tenant> tenants, List<Property> resources,
      RentContract[][] contracts) {
    super(tenants, resources, contracts);
  }

  public void print(Double[][] matrix) {
    AtomicInteger length = new AtomicInteger();
    String block = Arrays.stream(matrix).map(row ->
        Arrays.stream(row)
            .map(d -> String.format("%s%.2f", d > 0 ? " " : "-", Math.abs(d)))
            .collect(Collectors.joining(" "))
    ).peek(r -> length.set(r.length())).collect(Collectors.joining("|\n|"));
    String dash = "=".repeat(length.get() + 2);
    String string = String.format("%s\n|%s|\n%s", dash, block, dash);
    System.out.println(string);
  }

  @Override
  public ExitCode processEvent(Contract<Landlord, Tenant, Property> contract) {
    return contract instanceof RentContract
      ? new RentPaymentEvent().process((RentContract) contract)
      : ExitCode.safe(ExitCode.ERROR);
  }

  @Override
  public void run() {
    super.run();
    print(getTruthTable());
  }
}

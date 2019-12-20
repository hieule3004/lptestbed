package com.lpinc.testbed.simulator.simulator;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.resource.Land;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EstateSimulator extends Simulator<Tenant, Land> {

  public EstateSimulator(List<Tenant> agents, List<Land> resources) {
    super(agents, resources);
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
  public Event createEvent(Tenant tenant, Land land) {
    return new RentPaymentEvent(tenant, land);
  }

  @Override
  public void run() {
    super.run();
    print(getTruthTable());
  }
}

package com.lpinc.testbed.simulator;

import com.lpinc.testbed.simulator.agent.Landlord;
import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.resource.Land;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        runSampleSimulator();
        runSingularSimulator(1, 50, 12);
    }

    public static void runSampleSimulator() {
        double[][] landPrices = new double[5][];
        double[] prices = new double[4];
        Arrays.fill(prices, 1);
        Arrays.fill(landPrices, prices);
        int[][] tenantValues = new int[20][2];
        Arrays.fill(tenantValues, new int[] {100000, 12});

        List<Land> resources = new ArrayList<>();
        for (double[] landPrice : landPrices) {
            Landlord landlord = new Landlord(landPrice,0.1);
            resources.addAll(landlord.getResources().parallelStream()
//                    .filter(r -> r instanceof Land)
                    .map(r -> (Land) r)
                    .collect(Collectors.toList()));
        }
        List<Tenant> agents = new ArrayList<>();
        for (int[] value : tenantValues) {
            Tenant tenant = new Tenant(value[0], value[1]);
            agents.add(tenant);
        }

        Simulator<Tenant, Land> simulator = new Simulator<>(agents, resources) {
            @Override
            public Event<Tenant, Land> generateEvent(Tenant tenant, Land land) {
                return new RentPaymentEvent(tenant, land);
            }
        };
        simulator.run();
    }

    public static void runSingularSimulator(double rent, double balance, int period) {
        List<Tenant> agents = new ArrayList<>();
        List<Land> resources = new ArrayList<>();

        double weight = 0.1;
        Landlord landlord = new Landlord(new double[] { rent }, weight);
        resources.add((Land) landlord.getResources().get(0));
        Tenant tenant = new Tenant(balance, period);
        agents.add(tenant);

        Simulator<Tenant, Land> simulator = new Simulator<>(agents, resources) {
            @Override
            public Event<Tenant, Land> generateEvent(Tenant tenant, Land land) {
                return new RentPaymentEvent(tenant, land);
            }
        };
        simulator.run();
    }
}

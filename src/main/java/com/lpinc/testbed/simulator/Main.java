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
        runSampleSimulator();
    }

    public static void runSampleSimulator() {
        double[][] landPrices = new double[5][];
        double[] prices = new double[4];
        Arrays.fill(prices, 1);
        Arrays.fill(landPrices, prices);
        double[][] tenantValues = new double[20][2];
        Arrays.fill(tenantValues, new double[] {100000, 0.8});

        List<Land> resources = new ArrayList<>();
        for (double[] landPrice : landPrices) {
            Landlord landlord = new Landlord(landPrice,0.1);
            resources.addAll(landlord.getResources().parallelStream()
//                    .filter(r -> r instanceof Land)
                    .map(r -> (Land) r)
                    .collect(Collectors.toList()));
        }
        List<Tenant> agents = new ArrayList<>();
        for (double[] value : tenantValues) {
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
}

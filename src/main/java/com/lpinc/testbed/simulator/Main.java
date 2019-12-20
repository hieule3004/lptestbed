package com.lpinc.testbed.simulator;

import com.lpinc.testbed.simulator.agent.Tenant;
import com.lpinc.testbed.simulator.principal.Landlord;
import com.lpinc.testbed.simulator.resource.Land;
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
        Repeat.fill(landPrices, i -> 1.0);
        int[][] tenantValues = new int[20][2];
        Repeat.parallel(20, i -> tenantValues[i] = new int[] {1000, 12});

        List<Land> resources = Arrays.stream(landPrices).flatMap(price -> {
            Landlord landlord = new Landlord(price, 0.1);
            return landlord.getResources().stream();
        }).collect(Collectors.toList());
        List<Tenant> agents = Arrays.stream(tenantValues)
                .map(value -> new Tenant(value[0], value[1]))
                .collect(Collectors.toList());
        EstateSimulator simulator = new EstateSimulator(agents, resources);
        simulator.run();
    }

    public static void runSingularSimulator(double rent, double balance, int period) {
        List<Tenant> agents = new ArrayList<>() {{ add(new Tenant(balance, period)); }};
        Landlord landlord = new Landlord(new Double[] { rent }, 0.1);
        List<Land> resources = new ArrayList<>(landlord.getResources());

        EstateSimulator simulator = new EstateSimulator(agents, resources);
        simulator.run();
    }
}

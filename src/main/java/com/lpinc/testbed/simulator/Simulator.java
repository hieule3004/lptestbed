package com.lpinc.testbed.simulator;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.resource.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Simulator<A extends Agent, R extends Resource> {

    private List<A> agents;
    private List<R> resources;
//    private final List<Event<A, R>> log = new ArrayList<>();

    private final double[][] reputations;
    private final double[][] calculatedTruths;

    protected Simulator(List<A> agents, List<R> resources) {
        this.agents = agents;
        this.resources = resources;
        //init tables
        reputations = new double[agents.size()][resources.size()];
        calculatedTruths = new double[agents.size()][resources.size()];
        double[] row = new double[resources.size()];
        Arrays.fill(row, 0.5);
        for (int i = 0; i < agents.size(); i++) {
            calculatedTruths[i] = Arrays.copyOf(row, row.length);
        }
    }

    public void run() {
        int runs = 5000;
        agents.parallelStream().forEach(agent -> resources.parallelStream().forEach(resource -> {
            int row = resource.getID();
            int col = agent.getID();
            double weight = resource.getOwner().getRepWeight();
//                log.addAll(IntStream.range(0, runs).parallel().mapToObj(i -> {
            IntStream.range(0, runs).parallel().forEach(i -> {
                //trigger event
                Event<A, R> event = generateEvent(agent, resource);
                event.process(this);
                //update reputation
                int sub = event.getResult() ? 0 : 1;
                double val = weight - sub;
                reputations[row][col] += val;
            });
//                    return event;
//                }).collect(Collectors.toList()));
            //update truth value
            double p = calculatedTruths[row][col];
            double mean = (p * weight - (1 - p) * (1 - weight)) * runs;
            calculatedTruths[row][col] += (reputations[row][col] - mean) / runs;
        }));
        System.out.println(Arrays.deepToString(calculatedTruths));
    }

    public abstract Event<A, R> generateEvent(A agent, R resource);
}

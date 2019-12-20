package com.lpinc.testbed.simulator.simulator;

import com.google.common.util.concurrent.AtomicDouble;
import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.resource.Resource;
import com.lpinc.testbed.simulator.utils.DimensionLock;
import com.lpinc.testbed.simulator.utils.ExitCode;
import com.lpinc.testbed.simulator.utils.Repeat;

import java.util.List;

public abstract class Simulator<A extends Agent, R extends Resource<?>> {

    private List<A> agents;
    private List<R> resources;

    private AtomicDouble[][] reputations;
    private Double[][] calculatedTruths;
    private DimensionLock lock;

    public Simulator(List<A> agents, List<R> resources) {
        this.agents = agents;
        this.resources = resources;
        //init tables
        reputations = new AtomicDouble[agents.size()][resources.size()];
        Repeat.fill(reputations, i -> new AtomicDouble(0.0));
        calculatedTruths = new Double[agents.size()][resources.size()];
        Repeat.fill(calculatedTruths, i -> 0.5);
        lock = new DimensionLock(agents.size(), resources.size());
    }

    public Double[][] getCalculatedTruths() {
        return calculatedTruths;
    }

    public void run() {
        int runs = 5000;
        Repeat.parallel(agents.size(), i -> Repeat.parallel(resources.size(), j -> {
            double weight = resources.get(j).getOwner().getRepWeight();
            Repeat.parallel(runs, k -> {
                //trigger event
                lock.lock(i, j);
                Event event = createEvent(agents.get(i), resources.get(j));
                ExitCode result = event.process();
                lock.unlock(i, j);
                //update reputation
                reputations[i][j].addAndGet(weight - (result == ExitCode.SUCCESS ? 0 : 1));
            });
            double prob = calculatedTruths[i][j];
            double mean = (prob * weight - (1 - prob) * (1 - weight)) * runs;
            calculatedTruths[i][j] += (reputations[i][j].get() - mean) / runs;
        }));
    }

    public abstract Event createEvent(A agent, R resource);
}

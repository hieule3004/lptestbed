package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.ExitCode;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.resource.BankAccount;
import com.lpinc.testbed.simulator.resource.Resource;

import java.util.Random;

public class Tenant extends Agent {

    private static int idCount = 0;

    private final int ID;
    private double honesty;
    private BankAccount bankAccount;

    private Random random = new Random();

    public Tenant(double balance, double likeness) {
        super(Role.TENANT);
        this.ID = idCount++;
        this.honesty = likeness;
        this.bankAccount = new BankAccount(this, balance);
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public double getRepWeight() {
        return 0;
    }

    @Override
    public int request(Event<? extends Agent, ? extends Resource> event) {
        return ExitCode.ERROR;
    }

    @Override
    public int response(Event<? extends Agent, ? extends Resource> event) {
        if (random.nextDouble() > honesty) return ExitCode.FAILURE;
        if (event instanceof RentPaymentEvent) {
            double rent = ((RentPaymentEvent) event).getRent();
            if (rent <= bankAccount.getBalance()) {
                bankAccount.transact(-rent);
                return ExitCode.SUCCESS;
            } else {
                return ExitCode.FAILURE;
            }
        }
        return ExitCode.ERROR;
    }
}

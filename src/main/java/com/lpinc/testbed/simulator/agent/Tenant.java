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
    private final BankAccount bankAccount;
    private final int period;
    private final double balance;
    private int countdown;

    private final double honesty;

    private Random random = new Random();

    public Tenant(double balance, int period) {
        super(Role.TENANT);
        this.balance = balance;
        this.ID = idCount++;
        this.honesty = random.nextDouble();
        this.bankAccount = new BankAccount(this, balance);
        this.period = period;
        this.countdown = period;
//        System.out.println(honesty);
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

    private void checkBalance() {
        countdown--;
        if (countdown == 0) {
            bankAccount.transact(balance - bankAccount.getBalance());
            countdown = period;
        }
    }

    //check balance to handle long loop in simulation
    @Override
    public int response(Event<? extends Agent, ? extends Resource> event) {
        checkBalance();
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

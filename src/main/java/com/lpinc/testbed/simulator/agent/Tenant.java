package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.RentPaymentEvent;
import com.lpinc.testbed.simulator.utils.ExitCode;

import java.util.Random;

public class Tenant implements Agent {

    private static int idCount = 0;

    private final int ID;
    private final double balance;
    private final int period;

    private double currentBalance;
    private int countdown;
    private final double honesty;
    private Random random = new Random();

    public Tenant(double balance, int period) {
        this.ID = idCount++;
        this.balance = balance;
        this.period = period;
        this.currentBalance = balance;
        this.countdown = period;
        this.honesty = 0.8;
//        System.out.println(honesty);
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public ExitCode request(Event event) {
        return ExitCode.ERROR;
    }

    @Override
    public ExitCode response(Event event) {
        countdown--;
        if (countdown == 0) {
            currentBalance = balance;
            countdown = period;
        }

        if (random.nextDouble() > honesty) return ExitCode.FAILURE;

        if (event instanceof RentPaymentEvent) {
            double rent = ((RentPaymentEvent) event).getRent();
            if (rent <= currentBalance) {
                currentBalance -= rent;
                return ExitCode.SUCCESS;
            } else {
                return ExitCode.FAILURE;
            }
        }
        return ExitCode.ERROR;
    }

    @Override
    public String toString() {
        return String.join(" ", getClass().getSimpleName(), String.valueOf(ID));
    }
}

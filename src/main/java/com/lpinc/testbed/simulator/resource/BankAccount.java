package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.agent.Agent;

public class BankAccount extends Resource {

    private Agent owner;
    private double balance;

    public BankAccount(Agent owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void transact(double value) {
        this.balance += value;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Agent getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.format("Bank[%s]", balance);
    }
}

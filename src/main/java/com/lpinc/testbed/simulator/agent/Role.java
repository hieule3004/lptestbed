package com.lpinc.testbed.simulator.agent;

public enum Role {
    LANDLORD,
    TENANT;

    public String getFormat() {
        return toString().charAt(0) + toString().substring(1).toLowerCase();
    }
}

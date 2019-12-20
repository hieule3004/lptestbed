package com.lpinc.testbed.simulator.resource;

import com.lpinc.testbed.simulator.principal.Principal;

public interface Resource<P extends Principal<P>> {

  P getOwner();
}

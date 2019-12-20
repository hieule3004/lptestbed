package com.lpinc.testbed.simulator.principal;

import com.lpinc.testbed.simulator.resource.Resource;

import java.util.List;

public interface Principal<P extends Principal<P>> {

  List<? extends Resource<P>> getResources();
}

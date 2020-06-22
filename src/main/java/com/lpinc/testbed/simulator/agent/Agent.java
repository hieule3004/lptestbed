package com.lpinc.testbed.simulator.agent;

import com.lpinc.testbed.simulator.event.request.Request;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.utils.Data;
import javax.validation.constraints.NotNull;

public interface Agent extends Data {

  @NotNull
  Response<?> reply(Request request);

  double getJudgement();

  void updateJudgement(double offset);

  int getRatingCount();

  void updateRatingCount(int number);
}

package com.lpinc.testbed.simulator.event.request;

import com.lpinc.testbed.simulator.contract.Clause;
import com.lpinc.testbed.simulator.event.Event;
import com.lpinc.testbed.simulator.event.tag.RequestType;
import com.lpinc.testbed.simulator.event.response.Response;
import com.lpinc.testbed.simulator.runner.Simulator;
import java.time.LocalDate;

public abstract class Request extends Event {

  private RequestType type;
  private Clause clause;

  public Request(LocalDate date, RequestType type, Clause clause) {
    super(date);
    this.type = type;
    this.clause = clause;
  }

  public final RequestType getType() {
    return type;
  }

  public final Clause getClause() {
    return clause;
  }

  public final Response<?> check(Simulator simulator, int branch) {
    Response<?> response = clause.getAgent().reply(this);
    if (response.ok()) {
      action(simulator, response, branch);
    }
    return response;
  }

  public abstract void action(Simulator simulator, Response<?> response, int branch);
}

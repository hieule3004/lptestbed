package com.lpinc.testbed.simulator.contract;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.event.request.Request;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Contract {

  private final Map<Clause, List<Request>> clauses;
  private List<Agent> agents;
  private int duration;

  public Contract(List<Agent> agents, int duration) {
    this.agents = agents;
    this.duration = duration;
    this.clauses = new LinkedHashMap<>();
  }

  public final int length() {
    return clauses.size();
  }

  public List<Request> getObligations(Clause clause) {
    return clauses.get(clause);
  }

  protected void addObligations(Clause clause, List<Request> obligations) {
    clauses.put(clause, obligations);
  }

  public List<Clause> clausesList() {
    return new ArrayList<>(clauses.keySet());
  }

  public List<Agent> getAgents() {
    return agents;
  }

  public int getDuration() {
    return duration;
  }
}

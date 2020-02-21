package com.lpinc.testbed.simulator.event;

import java.time.LocalDate;

public abstract class Event implements Comparable<Event> {

  private LocalDate date;

  protected Event(LocalDate date) {
    this.date = date;
  }

  @Override
  public int compareTo(Event o) {
    return date.compareTo(o.date);
  }
}

package com.lpinc.testbed.simulator.utils;

public enum ExitCode {
  SUCCESS,
  FAILURE,
  ERROR;

  public static ExitCode safe(ExitCode code) {
    if (code == ExitCode.ERROR) {
      throw new UnsupportedOperationException("Error occurred");
    }
    return code;
  }
}

package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InsufficientAvailability extends Exception {
  public InsufficientAvailability(String message) {
    super(message);
  }
}

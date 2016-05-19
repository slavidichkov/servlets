package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUser {
  private final User user;
  private final String sessionID;

  public CurrentUser(User user, String sessionID) {
    this.user = user;
    this.sessionID = sessionID;
  }

  public User getUser() {
    return user;
  }

  public String getSessionID() {
    return sessionID;
  }
}

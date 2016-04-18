package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUserProviderImpl implements CurrentUserProvider {
  public CurrentUser get(SessionFinder sessionFinder){
    return new CurrentUserImpl (sessionFinder);
  }
}

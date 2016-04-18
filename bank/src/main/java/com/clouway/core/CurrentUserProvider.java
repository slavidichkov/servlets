package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface CurrentUserProvider {
  CurrentUser get(SessionFinder sessionFinder);
}

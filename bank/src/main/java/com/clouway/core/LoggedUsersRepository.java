package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface LoggedUsersRepository {
  void login(User user);
  void logout(User user);
  int getCount();
}

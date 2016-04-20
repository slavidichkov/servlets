package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface UserRepository {
  Optional<User> getUser(String email);

  void register(User user);
}

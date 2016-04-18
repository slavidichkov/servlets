package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface CurrentUser {
    Optional<User> getUser();
    String getSid();
}

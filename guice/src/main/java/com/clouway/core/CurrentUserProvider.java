package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface CurrentUserProvider {
  Optional<CurrentUser> get(SidGatherer sidGatherer);
}

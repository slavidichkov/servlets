package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUserProviderImpl implements CurrentUserProvider {
  private SessionsRepository sessionRepository;
  private UsersRepository userRepository;

  @Inject
  public CurrentUserProviderImpl(SessionsRepository sessionRepository, UsersRepository userRepository) {
    this.sessionRepository = sessionRepository;
    this.userRepository = userRepository;
  }

  public Optional<CurrentUser> get(SidGatherer sidGatherer){
    Optional<Session> optSession = sessionRepository.getSession(sidGatherer.getSid());
    if (!optSession.isPresent()){
      return Optional.absent();
    }
    Session session=optSession.get();
    Optional<User> optUser = userRepository.getUser(session.userEmail);
    if (optUser.isPresent()) {
      return Optional.of(new CurrentUser(optUser.get(),session.ID));
    }
    return Optional.absent();
  }
}

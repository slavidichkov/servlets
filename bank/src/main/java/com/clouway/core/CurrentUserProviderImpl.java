package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUserProviderImpl implements CurrentUserProvider {
  private final SessionsRepositoryFactory sessionsRepositoryFactory = DependencyManager.getDependency(SessionsRepositoryFactory.class);
  private final UsersRepositoryFactory userRepositoryFactory = DependencyManager.getDependency(UsersRepositoryFactory.class);
  private SessionsRepository sessionRepository = sessionsRepositoryFactory.getSessionRepository();
  private UsersRepository userRepository=userRepositoryFactory.getUserRepository();

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

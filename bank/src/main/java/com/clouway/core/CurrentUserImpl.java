package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUserImpl implements CurrentUser{
    private final SessionsRepositoryFactory sessionsRepositoryFactory = DependencyManager.getDependency(SessionsRepositoryFactory.class);
    private final UsersRepositoryFactory userRepositoryFactory = DependencyManager.getDependency(UsersRepositoryFactory.class);
    private SessionsRepository sessionRepository = sessionsRepositoryFactory.getSessionRepository();
    private UsersRepository userRepository=userRepositoryFactory.getUserRepository();
    private SessionFinder sessionFinder;



    public Optional<User> getUser() {
        Optional<Session> optSession = sessionRepository.getSession(sessionFinder.getId());
        if (optSession.isPresent()) {
            Session session=optSession.get();
            Optional<User> optUser = userRepository.getUser(session.userEmail);
            return optUser;
        }
        return Optional.absent();
    }

    public String getSid() {
        return sessionFinder.getId();
    }

    public void set(SessionFinder sessionFinder) {
        this.sessionFinder = sessionFinder;
    }
}

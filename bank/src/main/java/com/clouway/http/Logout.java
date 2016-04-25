package com.clouway.http;

import com.clouway.core.*;
import com.clouway.http.authorization.CookieSessionFinder;
import com.google.common.base.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Logout extends HttpServlet {
    private final SessionsRepositoryFactory sessionsRepositoryFactory = DependencyManager.getDependency(SessionsRepositoryFactory.class);
    private LoggedUsersRepositoryFactory loggedUsersRepositoryFactory=DependencyManager.getDependency(LoggedUsersRepositoryFactory.class);
    private final CurrentUserProvider currentUserProvider = DependencyManager.getDependency(CurrentUserProvider.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionsRepository sessionRepository = sessionsRepositoryFactory.getSessionRepository();
        LoggedUsersRepository loggedUsersRepository=loggedUsersRepositoryFactory.getLoggedUsersRepository();

        Optional<CurrentUser> optCurrentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));

        if (optCurrentUser.isPresent()) {
            String sid = optCurrentUser.get().getSessionID();
            sessionRepository.remove(sid);
            loggedUsersRepository.logout(optCurrentUser.get().getUser());
        }
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

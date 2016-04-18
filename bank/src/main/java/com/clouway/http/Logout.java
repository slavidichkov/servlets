package com.clouway.http;

import com.clouway.core.LoggedUsers;
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
    private final CurrentUserProvider currentUserProvider = DependencyManager.getDependency(CurrentUserProvider.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionsRepository sessionRepository = sessionsRepositoryFactory.getSessionRepository();

        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));
        Optional<User> optUser = currentUser.getUser();

        if (optUser.isPresent()) {
            String sid = currentUser.getSid();
            sessionRepository.remove(sid);
            LoggedUsers.logout(optUser.get());
        }
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

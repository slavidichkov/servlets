package com.clouway.http;

import com.clouway.core.*;
import com.clouway.http.authorization.CookieSidGatherer;
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

        Optional<CurrentUser> optCurrentUser = currentUserProvider.get(new CookieSidGatherer(req.getCookies()));

        if (optCurrentUser.isPresent()) {
            String sid = optCurrentUser.get().getSessionID();
            User user=optCurrentUser.get().getUser();
            sessionRepository.remove(new Session(sid,user.email));
        }
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

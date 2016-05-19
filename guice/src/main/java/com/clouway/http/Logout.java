package com.clouway.http;

import com.clouway.core.*;
import com.clouway.http.authorization.CookieSidGatherer;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
@Singleton
public class Logout extends HttpServlet {
    private final SessionsRepository sessionsRepository;
    private final CurrentUserProvider currentUserProvider;

    @Inject
    public Logout(SessionsRepository sessionsRepository, CurrentUserProvider currentUserProvider) {
        this.sessionsRepository = sessionsRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<CurrentUser> optCurrentUser = currentUserProvider.get(new CookieSidGatherer(req.getCookies()));

        if (optCurrentUser.isPresent()) {
            String sid = optCurrentUser.get().getSessionID();
            User user=optCurrentUser.get().getUser();
            sessionsRepository.remove(new Session(sid,user.email));
        }
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

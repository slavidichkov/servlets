package com.clouway.http;

import com.clouway.core.*;
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
    private final CurrentUser currentUser;

    @Inject
    public Logout(SessionsRepository sessionsRepository, CurrentUser currentUser) {
        this.sessionsRepository = sessionsRepository;
        this.currentUser = currentUser;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (currentUser !=null) {
            String sid = currentUser.getSessionID();
            User user= currentUser.getUser();
            sessionsRepository.remove(new Session(sid,user.email));
        }
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

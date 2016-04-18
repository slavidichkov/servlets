package com.clouway.http.authorization;

import com.clouway.core.*;
import com.google.common.base.Optional;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SecurityFilter implements Filter {
    private SessionsRepositoryFactory repositoryFactory = DependencyManager.getDependency(SessionsRepositoryFactory.class);
    private CurrentUserProvider currentUserProvider = DependencyManager.getDependency(CurrentUserProvider.class);
    private SessionsRepository sessionRepository = repositoryFactory.getSessionRepository();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;


        Cookie[] cookies = req.getCookies();
        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));
        String sid = currentUser.getSid();

        Optional<Session> optSession = sessionRepository.getSession(sid);
        if (optSession.isPresent()) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("/login");
        }
    }

    public void destroy() {

    }
}

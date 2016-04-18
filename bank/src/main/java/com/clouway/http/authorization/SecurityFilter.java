package com.clouway.http.authorization;

import com.clouway.core.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SecurityFilter implements Filter {
    private CurrentUserProvider currentUserProvider = DependencyManager.getDependency(CurrentUserProvider.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        CurrentUser currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));

        if (currentUser.getUser().isPresent()) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("/login");
        }
    }

    public void destroy() {

    }
}

package com.clouway.http.authorization;

import com.clouway.core.*;
import com.google.common.base.Optional;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SecurityFilter implements Filter {
    private final HashSet<String> allowedPages;
    private CurrentUserProvider currentUserProvider = DependencyManager.getDependency(CurrentUserProvider.class);

    public SecurityFilter(HashSet<String> allowedPages) {
        this.allowedPages = allowedPages;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri=req.getRequestURI();

        Optional<CurrentUser> currentUser = currentUserProvider.get(new CookieSessionFinder(req.getCookies()));
        String endpoint="";
        String[] endpoints=uri.split("/");
        if (endpoints.length>0){
            endpoint=endpoints[1];
        }
        if (!currentUser.isPresent() && allowedPages.contains(endpoint)){
            chain.doFilter(request, response);
            return;
        }
        if (currentUser.isPresent()) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("/");
        }
    }

    public void destroy() {

    }
}

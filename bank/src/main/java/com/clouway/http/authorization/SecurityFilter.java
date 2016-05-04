package com.clouway.http.authorization;

import com.clouway.core.*;
import com.google.common.base.Optional;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SecurityFilter implements Filter {
    private final Set<String> allowedPages;
    private final SessionsRepositoryFactory sessionsRepositoryFactory = DependencyManager.getDependency(SessionsRepositoryFactory.class);
    private SessionsRepository sessionRepository = sessionsRepositoryFactory.getSessionRepository();

    public SecurityFilter(Set<String> allowedPages) {
        this.allowedPages = allowedPages;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri=req.getRequestURI();
        SidGatherer sidGatherer = new CookieSidGatherer(req.getCookies());

        Optional<Session> optSession = sessionRepository.getSession(sidGatherer.getSid());

        String endpoint="";
        String[] endpoints=uri.split("/");
        if (endpoints.length>0){
            endpoint=endpoints[1];
        }
        if (endpoint.equals("errorpage")){
            resp.sendRedirect("/");
            return;
        }
        if (optSession.isPresent() && optSession.get().isExpired()){
            sessionRepository.remove(optSession.get());
            resp.sendRedirect("/login");
            return;
        }
        if (optSession.isPresent() && !optSession.get().isExpired()){
            sessionRepository.updateSessionExpiresOn(optSession.get().ID);
        }
        if (isPageAllowed(optSession, endpoint)|| (endpoint.equals("logout")) || (endpoint.equals(""))){
            chain.doFilter(request, response);
            return;
        }
        resp.sendRedirect("/");

    }

    private boolean isPageAllowed(Optional<Session> optSession, String endpoint) {
        if (optSession.isPresent()){
            return !allowedPages.contains(endpoint);
        }
        return allowedPages.contains(endpoint);
    }

    public void destroy() {

    }
}

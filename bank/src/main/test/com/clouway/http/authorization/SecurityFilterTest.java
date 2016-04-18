package com.clouway.http.authorization;


import com.clouway.core.*;
import com.clouway.http.fakeclasses.FakeCurrentUser;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SecurityFilterTest {
    private SecurityFilter securityFilter;
    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;
    private FakeCurrentUser currentUser;


    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    FilterChain filterChain;

    @Mock
    SessionsRepository sessionsRepository;

    @Before
    public void setUp() {
        currentUser=new FakeCurrentUser();

        DependencyManager.addDependencies(SessionsRepositoryFactory.class, new SessionsRepositoryFactory() {
            public SessionsRepository getSessionRepository() {
                return sessionsRepository;
            }
        });
        DependencyManager.addDependencies(CurrentUserProvider.class, new CurrentUserProvider() {
            public CurrentUser get(SessionFinder sessionFinder) {
                return currentUser;
            }
        });

        securityFilter = new SecurityFilter();
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }


    @Test
    public void loggedUser() throws IOException, ServletException {
        final Cookie cookie=new Cookie("sid","1234567890");
        request.addCookies(cookie);

        currentUser.setSid("1234567890");
        currentUser.setUser(new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23));

        context.checking(new Expectations() {{
            oneOf(filterChain).doFilter(request, response);
        }});

        securityFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void notLoggedUser() throws IOException, ServletException {
        currentUser.setUser(null);

        context.checking(new Expectations() {{
            never(filterChain).doFilter(request, response);
        }});

        securityFilter.doFilter(request, response, filterChain);

        assertThat(response.getRedirectUrl(), is(equalTo("/login")));
    }
}
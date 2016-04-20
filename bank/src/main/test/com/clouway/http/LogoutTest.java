package com.clouway.http;

import com.clouway.core.*;
import com.clouway.http.fakeclasses.FakeCurrentUser;
import com.clouway.http.fakeclasses.FakeRequest;
import com.clouway.http.fakeclasses.FakeResponse;
import com.clouway.http.fakeclasses.FakeSession;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class LogoutTest {
    private Logout logout;
    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;
    private FakeCurrentUser currentUser=new FakeCurrentUser();

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    SessionsRepository sessionsRepository;

    @Mock
    LoggedUsersRepository loggedUsersRepository;

    @Before
    public void setUp() throws Exception {
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
        DependencyManager.addDependencies(LoggedUsersRepositoryFactory.class, new LoggedUsersRepositoryFactory() {
            public LoggedUsersRepository getLoggedUsersRepository() {
                return loggedUsersRepository;
            }
        });
        logout = new Logout();
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }

    @Test
    public void happyPath() throws ServletException, IOException {
        final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
        final String sid = "1234567890";
        final Cookie cookie = new Cookie("sid", sid);

        request.addCookies(cookie);

        currentUser.setUser(user);
        currentUser.setSid(sid);

        context.checking(new Expectations() {{
            oneOf(sessionsRepository).remove(sid);
            oneOf(loggedUsersRepository).logout(user);
        }});

        logout.doPost(request, response);
        assertThat(response.getRedirectUrl(), is(equalTo("/")));
    }
}
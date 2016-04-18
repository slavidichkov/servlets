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
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AccountManagerTest {
    private AccountManager accountManager;
    private FakeRequest request;
    private FakeResponse response;
    private FakeSession session;
    private FakeCurrentUser currentUser=new FakeCurrentUser();

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    AccountsRepository accountsRepository;

    @Before
    public void setUp() throws Exception {
        DependencyManager.addDependencies(AccountsRepositoryFactory.class, new AccountsRepositoryFactory() {
            public AccountsRepository getAccountRepository() {
                return accountsRepository;
            }
        });
        DependencyManager.addDependencies(CurrentUserProvider.class, new CurrentUserProvider() {
            public CurrentUser get(SessionFinder sessionFinder) {
                return currentUser;
            }
        });
        DependencyManager.addDependencies(UserValidator.class,new RegularExpressionUserValidator());

        accountManager = new AccountManager();
        session = new FakeSession();
        request = new FakeRequest(session);
        response = new FakeResponse();
    }

    @Test
    public void userBalance() throws IOException, ServletException {
        final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
        final Cookie cookie=new Cookie("sid","1234567890");
        request.addCookies(cookie);

        currentUser.setUser(user);

        context.checking(new Expectations() {{
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(22.23));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        accountManager.doGet(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Balance is : 22.23"), is(true));
    }

    @Test
    public void userWithdraw() throws IOException, ServletException, InsufficientAvailability {
        final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
        final Cookie cookie=new Cookie("sid","1234567890");
        request.addCookies(cookie);

        request.setParameter("transactionType", "withdraw");
        request.setParameter("amount", "23.12");

        currentUser.setUser(user);

        context.checking(new Expectations() {{
            oneOf(accountsRepository).withdraw(user,23.12);
            will(returnValue(0.0));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(0.0));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        accountManager.doPost(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Balance is : 0.0"), is(true));
    }

    @Test
    public void userDeposit() throws IOException, ServletException {
        final User user = new User("ivan", "ivan1313", "ivan@abv.bg", "ivan123", "sliven", 23);
        final Cookie cookie=new Cookie("sid","1234567890");
        request.addCookies(cookie);

        currentUser.setUser(user);

        request.setParameter("transactionType", "deposit");
        request.setParameter("amount", "23.12");

        context.checking(new Expectations() {{
            oneOf(accountsRepository).deposit(user,23.12);
            will(returnValue(23.12));
            oneOf(accountsRepository).getBalance(user);
            will(returnValue(23.12));
        }});

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.setOutputStream(out);

        accountManager.doPost(request, response);

        String expected = out.toString();
        assertThat(expected.contains("Balance is : 23.12"), is(true));
    }

}
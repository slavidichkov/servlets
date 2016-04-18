package com.clouway.core;

import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CurrentUserImplTest {
  private CurrentUserImpl currentUser;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  SessionsRepository sessionsRepository;

  @Mock
  UsersRepository usersRepository;

  @Mock
  SessionFinder sessionFinder;

  @Before
  public void setUp() {
    DependencyManager.addDependencies(UsersRepositoryFactory.class, new UsersRepositoryFactory() {
      public UsersRepository getUserRepository() {
        return usersRepository;
      }
    });
    DependencyManager.addDependencies(SessionsRepositoryFactory.class, new SessionsRepositoryFactory() {
      public SessionsRepository getSessionRepository() {
        return sessionsRepository;
      }
    });
    currentUser = new CurrentUserImpl(sessionFinder);
  }

  @Test
  public void getUserFromExistingSession() {
    final Session session = new Session("123456789", "nikola3423@abv.bg", 1234556778);
    final User user=new User("nikola","nikola123","nikola3423@abv.bg","nikola321","sliven",23);

    context.checking(new Expectations() {{
      oneOf(sessionFinder).getId();
      will(returnValue("123456789"));
      oneOf(sessionsRepository).getSession("123456789");
      will(returnValue(Optional.of(session)));
      oneOf(usersRepository).getUser("nikola3423@abv.bg");
      will(returnValue(Optional.of(user)));
    }});

    Optional<User> optUser = currentUser.getUser();

    assertThat(optUser.isPresent(),is(true));
    assertThat(optUser.get(),is(equalTo(user)));
  }

  @Test
  public void getUserFromNotExistingSession() {
    context.checking(new Expectations() {{
      oneOf(sessionFinder).getId();
      will(returnValue("123456789"));
      oneOf(sessionsRepository).getSession("123456789");
      will(returnValue(Optional.absent()));
      never(usersRepository).getUser("nikola3423@abv.bg");
    }});

    Optional<User> optUser = currentUser.getUser();

    assertThat(optUser.isPresent(),is(false));
  }
}
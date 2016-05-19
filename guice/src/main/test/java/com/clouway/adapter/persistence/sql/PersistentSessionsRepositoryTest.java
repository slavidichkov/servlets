package com.clouway.adapter.persistence.sql;

import com.clouway.core.Time;
import com.google.inject.Injector;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepositoryTest {
  private PersistentSessionsRepository sessionsRepository;
  private PersistentUsersRepository usersRepository;
  private long timeNow = 1459234212051L;
  private final long sessionExpiresTime = 1000 * 60 * 60 * 5;
  protected Injector injector;

  private class FakeTime implements Time {
    public Date now() {
      return new Date(timeNow);
    }

    public Date after(int hour) {
      return new Date(now().getTime() + 1000*60*60*hour);
    }
  }

//  @Before
//  public void setUp() {
//    final DataSource dataSource = new TestingDatasource().get();
//    new DatabaseCleaner(dataSource, "sessions", "users", "accounts", "loggedusers").cleanUp();
//
//    injector= Guice.createInjector(new AbstractModule() {
//      @Override
//      protected void configure() {
//        bind(Time.class).to(FakeTime.class);
//        bind(LoggedUsersRepository.class).to(PersistentLoggedUsersRepository.class);
//        bind(DataSourceProvider.class).to(DataSourceProviderImpl.class);
//        bind(DatabaseHelper.class).to(DatabaseHelperImpl.class);
//      }
//    });
//
//    sessionsRepository = injector.getInstance(PersistentSessionsRepository.class);
//    usersRepository = injector.getInstance(PersistentUsersRepository.class);
//  }
//
//  @Test
//  public void registeredSession() {
//    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
//    Session session = new Session("qwertyuiop", "ivan@abv.bg");
//    sessionsRepository.register(session);
//
//    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
//    assertThat(optSession.isPresent(), is(true));
//
//    Session expectedSession = optSession.get();
//    assertThat(session, is(equalTo(expectedSession)));
//    assertThat(expectedSession.getSessionExpiresOn(), is(equalTo(timeNow + sessionExpiresTime)));
//  }
//
//  @Test
//  public void removeSession() {
//    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
//    Session session = new Session("qwertyuiop", "ivan@abv.bg");
//
//    sessionsRepository.register(session);
//    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
//    assertThat(optSession.isPresent(), is(true));
//
//    sessionsRepository.remove(session);
//    Optional<Session> optRemovedSession = sessionsRepository.getSession("qwertyuiop");
//    assertThat(optRemovedSession.isPresent(), is(false));
//  }
}
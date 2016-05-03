package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.adapter.persistence.sql.util.TestingDatasource;
import com.clouway.core.DependencyManager;
import com.clouway.core.LoggedUsersRepository;
import com.clouway.core.LoggedUsersRepositoryFactory;
import com.clouway.core.Session;
import com.clouway.core.Time;
import com.clouway.core.User;
import com.google.common.base.Optional;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepositoryTest {
  private PersistentSessionsRepository sessionsRepository;
  private PersistentUsersRepository usersRepository;
  private FakeTime time=new FakeTime();
  private long timeNow = 1459234212051L;
  private final long sessionExpiresTime = 1000 * 60 * 60 * 5;

  private class FakeTime implements Time {
    public Date now() {
      return new Date(timeNow);
    }

    public Date after(int hour) {
      return new Date(now().getTime() + 1000*60*60*hour);
    }
  }

  @Before
  public void setUp() {
    final DataSource dataSource = new TestingDatasource().get();
    new DatabaseCleaner(dataSource, "sessions", "users", "accounts", "loggedusers").cleanUp();
    DependencyManager.addDependencies(LoggedUsersRepositoryFactory.class, new LoggedUsersRepositoryFactory() {
      public LoggedUsersRepository getLoggedUsersRepository() {
        return new PersistentLoggedUsersRepository(dataSource);
      }
    });
    DependencyManager.addDependencies(Time.class, time);
    sessionsRepository = new PersistentSessionsRepository(new DatabaseHelper(dataSource), sessionExpiresTime);
    usersRepository = new PersistentUsersRepository(new DatabaseHelper(dataSource));
  }

  @Test
  public void registeredSession() {
    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    Session session = new Session("qwertyuiop", "ivan@abv.bg");
    sessionsRepository.register(session);

    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optSession.isPresent(), is(true));

    Session expectedSession = optSession.get();
    assertThat(session, is(equalTo(expectedSession)));
    assertThat(expectedSession.getSessionExpiresOn(), is(equalTo(timeNow + sessionExpiresTime)));
  }

  @Test
  public void removeSession() {
    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    Session session = new Session("qwertyuiop", "ivan@abv.bg");

    sessionsRepository.register(session);
    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optSession.isPresent(), is(true));

    sessionsRepository.remove(session);
    Optional<Session> optRemovedSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optRemovedSession.isPresent(), is(false));
  }
}
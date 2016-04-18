package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.core.Session;
import com.clouway.core.User;
import com.google.common.base.Optional;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepositoryTest {
  private MysqlConnectionPoolDataSource dataSource;
  private PersistentSessionsRepository sessionsRepository;
  private PersistentUsersRepository usersRepository;

  @Before
  public void setUp() {
    dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/banktests");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    new DatabaseCleaner(dataSource, "sessions", "users", "accounts").cleanUp();
    sessionsRepository = new PersistentSessionsRepository(new DatabaseHelper(dataSource));
    usersRepository=new PersistentUsersRepository(new DatabaseHelper(dataSource));
  }

  @Test
  public void registeredSession() {
    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    Session session = new Session("qwertyuiop", "ivan@abv.bg", 123456789);
    sessionsRepository.register(session);

    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optSession.isPresent(), is(true));

    Session expectedSession = optSession.get();
    assertThat(session, is(equalTo(expectedSession)));
  }

  @Test
  public void removeSession() {
    usersRepository.register(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    Session session = new Session("qwertyuiop", "ivan@abv.bg", 123456789);

    sessionsRepository.register(session);
    Optional<Session> optSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optSession.isPresent(), is(true));

    sessionsRepository.remove("qwertyuiop");
    Optional<Session> optRemovedSession = sessionsRepository.getSession("qwertyuiop");
    assertThat(optRemovedSession.isPresent(), is(false));
  }
}
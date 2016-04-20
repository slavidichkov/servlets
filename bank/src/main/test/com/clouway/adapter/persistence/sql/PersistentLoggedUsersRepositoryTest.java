package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.adapter.persistence.sql.util.TestingDatasource;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentLoggedUsersRepositoryTest {
  private PersistentLoggedUsersRepository loggedUsersRepository;

  @Before
  public void setUp() {
    DataSource dataSource = new TestingDatasource().get();
    new DatabaseCleaner(dataSource, "loggedusers").cleanUp();
    loggedUsersRepository = new PersistentLoggedUsersRepository(dataSource);
  }

  @Test
  public void loginUser() {
    loggedUsersRepository.login(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    assertThat(loggedUsersRepository.getCount(), is(equalTo(1)));
  }

  @Test
  public void logoutUser() {
    loggedUsersRepository.login(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    assertThat(loggedUsersRepository.getCount(), is(equalTo(1)));

    loggedUsersRepository.logout(new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23));
    assertThat(loggedUsersRepository.getCount(), is(equalTo(0)));
  }
}
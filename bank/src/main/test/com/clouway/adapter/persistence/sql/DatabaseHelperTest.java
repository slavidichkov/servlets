package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.adapter.persistence.sql.util.FakeUserResultSetBuilder;
import com.clouway.core.User;
import com.google.common.base.Optional;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseHelperTest  {
  private MysqlConnectionPoolDataSource dataSource;

  @Rule
  public JUnitRuleMockery context=new JUnitRuleMockery();

  @Mock
  ResultSetBuilder<User> resultSetBuilder;

  @Before
  public void setUp() {
    dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/banktests");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    new DatabaseCleaner(dataSource, "users", "sessions", "accounts").cleanUp();
  }

  @Test
  public void existingObject() throws SQLException {
    Connection connection=dataSource.getConnection();
    SqlUsersRepository usersRepository=new SqlUsersRepository(dataSource);
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);

    long autoIncrementKey=DatabaseHelper.executeQuery(connection, "insert into users(userName,nickName,email,password,city,age) values(?,?,?,?,?,?)", user.name, user.nickName, user.email, user.password, user.city, user.age);
    assertThat(autoIncrementKey,is(equalTo(-1L)));
  }

  @Test
  public void existingObject1() throws SQLException {
    Connection connection=dataSource.getConnection();
    SqlUsersRepository usersRepository=new SqlUsersRepository(dataSource);
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    usersRepository.register(user);

    Optional<User> optUser = DatabaseHelper.executeQuery(connection,"select * from users where userName=?",new FakeUserResultSetBuilder(),"ivan");
    assertThat(optUser.isPresent(),is(true));
  }

}
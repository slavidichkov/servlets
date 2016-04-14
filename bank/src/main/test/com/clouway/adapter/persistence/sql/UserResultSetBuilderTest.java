package com.clouway.adapter.persistence.sql;

import com.clouway.core.User;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class UserResultSetBuilderTest {
  private UserResultSetBuilder userResultSetBuilder;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  ResultSet resultSet;

  @Before
  public void setUp(){
    userResultSetBuilder=new UserResultSetBuilder();
  }

  @Test
  public void buildExistingUser() throws SQLException {
    final User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    context.checking(new Expectations() {{
      oneOf(resultSet).next();
      will(returnValue(true));
      oneOf(resultSet).getString("userName");
      will(returnValue(user.name));
      oneOf(resultSet).getString("nickName");
      will(returnValue(user.nickName));
      oneOf(resultSet).getString("email");
      will(returnValue(user.email));
      oneOf(resultSet).getString("password");
      will(returnValue(user.password));
      oneOf(resultSet).getString("city");
      will(returnValue(user.city));
      oneOf(resultSet).getInt("age");
      will(returnValue(user.age));
    }});
     Optional<User> expectedUser = userResultSetBuilder.build(resultSet);
    assertThat(expectedUser.isPresent(),is(true));
  }

  @Test
  public void buildNotExistingUser() throws SQLException {
    final User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    context.checking(new Expectations() {{
      oneOf(resultSet).next();
      will(returnValue(false));
    }});
    Optional<User> expectedUser = userResultSetBuilder.build(resultSet);
    assertThat(expectedUser.isPresent(),is(false));
  }
}
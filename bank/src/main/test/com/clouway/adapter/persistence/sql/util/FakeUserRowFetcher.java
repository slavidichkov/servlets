package com.clouway.adapter.persistence.sql.util;

import com.clouway.adapter.persistence.sql.RowFetcher;
import com.clouway.core.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeUserRowFetcher implements RowFetcher<User> {

  public User build(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("userName"), resultSet.getString("nickName"), resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("city"),resultSet.getInt("age"));
  }
}

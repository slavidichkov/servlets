package com.clouway.adapter.persistence.sql;

import com.clouway.core.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class UserResultSetBuilder implements ResultSetBuilder<User>{

  public User build(ResultSet resultSet) {
    User user = null;
    try {
      user = new User(resultSet.getString("userName"), resultSet.getString("nickName"), resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("city"),resultSet.getInt("age"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }
}

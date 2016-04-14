package com.clouway.adapter.persistence.sql.util;

import com.clouway.adapter.persistence.sql.ResultSetBuilder;
import com.clouway.core.User;
import com.google.common.base.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeUserResultSetBuilder implements ResultSetBuilder<Optional<User>>{

  public Optional<User> build(ResultSet resultSet) {
    try {
      if (resultSet.next()){
        User user = new User(resultSet.getString("userName"), resultSet.getString("nickName"), resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("city"),resultSet.getInt("age"));
        return Optional.of(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.absent();
  }
}

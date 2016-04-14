package com.clouway.adapter.persistence.sql;

import com.clouway.core.User;
import com.clouway.core.UsersRepository;
import com.google.common.base.Optional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SqlUsersRepository implements UsersRepository {
  private final DataSource dataSource;

  public SqlUsersRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void register(User user) {
    Optional<Connection> optConnection=getConnection(dataSource);
    if (optConnection.isPresent()) {
      DatabaseHelper.executeQuery(optConnection.get(), "insert into users(userName,nickName,email,password,city,age) values(?,?,?,?,?,?)", user.name, user.nickName, user.email, user.password, user.city, user.age);
    }
  }

  public Optional<User> getUser(String email) {
    Optional<Connection> optConnection=getConnection(dataSource);
    if (optConnection.isPresent()) {
      User user = DatabaseHelper.executeQuery(optConnection.get(), "SELECT * FROM users WHERE email=?", new UserResultSetBuilder(), email);
      return Optional.of(user);
    }
    return Optional.absent();
  }

  private Optional<Connection> getConnection(DataSource dataSource){
    try {
      Optional.of(dataSource.getConnection());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.absent();
  }
}

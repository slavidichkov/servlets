package com.clouway.adapter.persistence.sql;

import com.clouway.core.User;
import com.clouway.core.UsersRepository;
import com.google.common.base.Optional;

import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentUsersRepository implements UsersRepository {
  private final DatabaseHelper databaseHelper;

  public PersistentUsersRepository(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public void register(User user) {
    try {
      databaseHelper.executeQuery("insert into users(userName,nickName,email,password,city,age) values(?,?,?,?,?,?)", user.name, user.nickName, user.email, user.password, user.city, user.age);
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }

  public Optional<User> getUser(String email) {
    try {
      return databaseHelper.executeQuery("SELECT * FROM users WHERE email=?", new UserResultSetBuilder(), email);
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }
}

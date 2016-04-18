package com.clouway.adapter.persistence.sql;

import com.clouway.core.Session;
import com.google.common.base.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SessionResultSetBuilder implements ResultSetBuilder<Optional<Session>> {
  public Optional<Session> build(ResultSet resultSet) {
    try {
      if (!resultSet.next()){
        Session session=new Session(resultSet.getString("ID"),resultSet.getString("userEmail"), resultSet.getLong("sessionExpiresOn"));
        return Optional.of(session);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.absent();
  }
}

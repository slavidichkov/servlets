package com.clouway.adapter.persistence.sql;

import com.clouway.core.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SessionResultSetBuilder implements ResultSetBuilder<Session> {
  public Session build(ResultSet resultSet) {
    Session session=null;
    try {
      session=new Session(resultSet.getString("ID"),resultSet.getString("userEmail"), resultSet.getLong("sessionExpiresOn"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return session;
  }
}

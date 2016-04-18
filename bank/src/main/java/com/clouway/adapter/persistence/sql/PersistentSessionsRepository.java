package com.clouway.adapter.persistence.sql;

import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.google.common.base.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepository implements SessionsRepository {
  private final DatabaseHelper databaseHelper;

  public PersistentSessionsRepository(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public void register(Session session) {
    try {
      databaseHelper.executeUpdate("INSERT INTO sessions(ID,userEmail,sessionExpiresOn) VALUES (?,?,?)", session.ID, session.userEmail, session.sessionExpiresOn);
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }

  public Optional<Session> getSession(String sessionID) {
    try {
      return databaseHelper.fetchOne("SELECT * FROM sessions WHERE ID=?", new RowFetcher<Session>() {
        public Session build(ResultSet resultSet) throws SQLException {
          return new Session(resultSet.getString("ID"), resultSet.getString("userEmail"), resultSet.getLong("sessionExpiresOn"));
        }
      }, sessionID);
    } catch (SQLException e) {
      throw new DatabaseException("");
    }
  }

  public void remove(String sessionID) {
    try {
      databaseHelper.executeUpdate("DELETE FROM sessions WHERE ID=?", sessionID);
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }
}

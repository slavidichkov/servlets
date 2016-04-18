package com.clouway.adapter.persistence.sql;

import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.google.common.base.Optional;

import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SqlSessionsRepository implements SessionsRepository {
  private final DatabaseHelper databaseHelper;

  public SqlSessionsRepository(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public void register(Session session) {
    try {
      databaseHelper.executeQuery("INSERT INTO sessions(ID,userEmail,sessionExpiresOn) VALUES (?,?,?)", session.ID, session.userEmail, session.sessionExpiresOn);
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }

  public Optional<Session> getSession(String sessionID) {
    try {
      return databaseHelper.executeQuery("SELECT * FROM sessions WHERE ID=?", new SessionResultSetBuilder(), sessionID);
    } catch (SQLException e) {
      throw new DatabaseException("");
    }
  }

  public void remove(String sessionID) {
    try {
      databaseHelper.executeQuery("DELETE FROM sessions WHERE ID=?", sessionID);
    } catch (SQLException e) {
      throw new DatabaseException();
    }
  }
}

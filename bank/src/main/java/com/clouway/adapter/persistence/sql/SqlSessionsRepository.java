package com.clouway.adapter.persistence.sql;

import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.google.common.base.Optional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SqlSessionsRepository implements SessionsRepository {
  private final DataSource dataSource;

  public SqlSessionsRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void register(Session session) {
    Optional<Connection> optConnection = getConnection(dataSource);
    if (optConnection.isPresent()) {
      DatabaseHelper.executeQuery(optConnection.get(), "INSERT INTO sessions(ID,userEmail,sessionExpiresOn) VALUES (?,?,?)", session.ID, session.userEmail, session.sessionExpiresOn);
    }
  }

  public Optional<Session> getSession(String sessionID) {
    Optional<Connection> optConnection = getConnection(dataSource);
    if (optConnection.isPresent()) {
      return DatabaseHelper.executeQuery(optConnection.get(), "SELECT * FROM sessions WHERE ID=?", new SessionResultSetBuilder(), sessionID);
    }
    return Optional.absent();
  }

  public void remove(String sessionID) {
    Optional<Connection> optConnection = getConnection(dataSource);
    if (optConnection.isPresent()){
      DatabaseHelper.executeQuery(optConnection.get(),"DELETE FROM sessions WHERE ID=?", sessionID);
    }
  }

  private Optional<Connection> getConnection(DataSource dataSource){
    try {
      return Optional.of(dataSource.getConnection());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.absent();
  }
}

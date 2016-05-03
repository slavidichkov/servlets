package com.clouway.adapter.persistence.sql;

import com.clouway.core.DependencyManager;
import com.clouway.core.LoggedUsersRepositoryFactory;
import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.clouway.core.Time;
import com.google.common.base.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepository implements SessionsRepository {
  private final DatabaseHelper databaseHelper;
  private final long sessionExpiresTime;
  private final Time time = DependencyManager.getDependency(Time.class);
  private LoggedUsersRepositoryFactory loggedUsersRepositoryFactory= DependencyManager.getDependency(LoggedUsersRepositoryFactory.class);

  public PersistentSessionsRepository(DatabaseHelper databaseHelper,long sessionExpiresTime) {
    this.databaseHelper = databaseHelper;
    this.sessionExpiresTime = sessionExpiresTime;
  }

  public void register(Session session) {
    databaseHelper.executeUpdate("INSERT INTO sessions(ID,userEmail,sessionExpiresOn) VALUES (?,?,?)", session.ID, session.userEmail,time.now().getTime()+sessionExpiresTime);
    loggedUsersRepositoryFactory.getLoggedUsersRepository().login(session.userEmail);
  }

  public Optional<Session> getSession(String sessionID) {
      return databaseHelper.fetchOne("SELECT * FROM sessions WHERE ID=?", new RowFetcher<Session>() {
        public Session build(ResultSet resultSet) throws SQLException {
          return new Session(resultSet.getString("ID"), resultSet.getString("userEmail"), resultSet.getLong("sessionExpiresOn"));
        }
      }, sessionID);
  }

  public void remove(Session session) {
    databaseHelper.executeUpdate("DELETE FROM sessions WHERE ID=?", session.ID);
    loggedUsersRepositoryFactory.getLoggedUsersRepository().logout(session.userEmail);
  }
}

package com.clouway.adapter.persistence.sql;

import com.clouway.core.LoggedUsersRepository;
import com.clouway.core.LoggedUsersRepositoryFactory;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentLoggedUsersRepositoryFactory implements LoggedUsersRepositoryFactory {
  public LoggedUsersRepository getLoggedUsersRepository() {
    DataSource dataSource = new AppDataSource().getConfiguredDataSource();
    return new PersistentLoggedUsersRepository(dataSource);
  }
}

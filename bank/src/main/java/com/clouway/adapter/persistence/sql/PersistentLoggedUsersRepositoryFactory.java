package com.clouway.adapter.persistence.sql;

import com.clouway.core.LoggedUsersRepository;
import com.clouway.core.LoggedUsersRepositoryFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentLoggedUsersRepositoryFactory implements LoggedUsersRepositoryFactory {
  public LoggedUsersRepository getLoggedUsersRepository() {
    MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/bank");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    return new PersistentLoggedUsersRepository(dataSource);
  }
}

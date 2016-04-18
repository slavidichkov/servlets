package com.clouway.adapter.persistence.sql;

import com.clouway.core.SessionsRepository;
import com.clouway.core.SessionsRepositoryFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SqlSessionsRepositoryFactory implements SessionsRepositoryFactory {
    public SessionsRepository getSessionRepository() {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/bank");
        dataSource.setUser("root");
        dataSource.setPassword("clouway.com");
        return new SqlSessionsRepository(new DatabaseHelper(dataSource));
    }
}

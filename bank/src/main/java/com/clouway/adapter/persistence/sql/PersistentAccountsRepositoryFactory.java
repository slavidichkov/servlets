package com.clouway.adapter.persistence.sql;

import com.clouway.core.AccountsRepository;
import com.clouway.core.AccountsRepositoryFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentAccountsRepositoryFactory implements AccountsRepositoryFactory {
    public AccountsRepository getAccountRepository() {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/bank");
        dataSource.setUser("root");
        dataSource.setPassword("clouway.com");
        return new PersistentAccountsRepository(dataSource);
    }
}

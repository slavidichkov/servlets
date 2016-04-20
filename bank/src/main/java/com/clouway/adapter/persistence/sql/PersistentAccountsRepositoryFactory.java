package com.clouway.adapter.persistence.sql;

import com.clouway.core.AccountsRepository;
import com.clouway.core.AccountsRepositoryFactory;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentAccountsRepositoryFactory implements AccountsRepositoryFactory {
    public AccountsRepository getAccountRepository() {
        DataSource dataSource = new AppDataSource().getConfiguredDataSource();
        return new PersistentAccountsRepository(dataSource);
    }
}

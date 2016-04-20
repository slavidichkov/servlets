package com.clouway.adapter.persistence.sql;

import com.clouway.core.UsersRepository;
import com.clouway.core.UsersRepositoryFactory;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentUsersRepositoryFactory implements UsersRepositoryFactory {
    public UsersRepository getUserRepository() {
        DataSource dataSource = new AppDataSource().getConfiguredDataSource();
        return new PersistentUsersRepository(new DatabaseHelper(dataSource));
    }
}

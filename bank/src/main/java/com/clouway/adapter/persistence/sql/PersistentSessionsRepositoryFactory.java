package com.clouway.adapter.persistence.sql;

import com.clouway.core.SessionsRepository;
import com.clouway.core.SessionsRepositoryFactory;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentSessionsRepositoryFactory implements SessionsRepositoryFactory {
    public SessionsRepository getSessionRepository() {
        DataSource dataSource = new AppDataSource().getConfiguredDataSource();
        return new PersistentSessionsRepository(new DatabaseHelper(dataSource), 1000 * 60 * 60 * 5);
    }
}

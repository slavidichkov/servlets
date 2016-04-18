package com.clouway.adapter.persistence.sql;

import com.clouway.core.UsersRepository;
import com.clouway.core.UsersRepositoryFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentUsersRepositoryFactory implements UsersRepositoryFactory {
    public UsersRepository getUserRepository() {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/bank");
        dataSource.setUser("root");
        dataSource.setPassword("clouway.com");

        return new PersistentUsersRepository(new DatabaseHelper(dataSource));
    }
}

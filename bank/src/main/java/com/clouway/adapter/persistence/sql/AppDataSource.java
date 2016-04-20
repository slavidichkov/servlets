package com.clouway.adapter.persistence.sql;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class AppDataSource {
  DataSource getConfiguredDataSource(){
    MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/bank");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    return dataSource;
  }
}

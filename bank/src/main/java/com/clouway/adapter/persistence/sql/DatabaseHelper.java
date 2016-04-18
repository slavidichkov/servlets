package com.clouway.adapter.persistence.sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseHelper {
  private final DataSource dataSource;

  public DatabaseHelper(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public long executeQuery(String query, Object... params) throws SQLException {
    long autoIncrementKey = -1;
    Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
    fillPreparedStatement(preparedStatement, params);
    preparedStatement.executeUpdate();

    ResultSet resultSet = preparedStatement.getGeneratedKeys();

    if (resultSet.next()) {
      autoIncrementKey = resultSet.getLong(1);
    }

    preparedStatement.close();
    connection.close();

    return autoIncrementKey;
  }

  public <T> T executeQuery(String query, ResultSetBuilder<T> resultSetBuilder, Object... params) throws SQLException {
    T object = null;
    Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    fillPreparedStatement(preparedStatement, params);
    ResultSet resultSet = preparedStatement.executeQuery();
    object = resultSetBuilder.build(resultSet);
    preparedStatement.close();
    connection.close();

    return object;
  }

  private void fillPreparedStatement(PreparedStatement preparedStatement, Object[] params) {

    for (int i = 0; i < params.length; i++) {
      try {
        preparedStatement.setObject(i + 1, params[i]);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
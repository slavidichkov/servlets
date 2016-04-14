package com.clouway.adapter.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseHelper {
  private DatabaseHelper() {
  }

  public static long executeQuery(Connection connection,String query, Object... params) {
    long autoIncrementKey = -1;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      fillPreparedStatement(preparedStatement, params);
      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()) {
        autoIncrementKey = resultSet.getLong(1);
      }

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return autoIncrementKey;
  }

  public static <T> T executeQuery(Connection connection, String query, ResultSetBuilder<T> resultSetBuilder, Object... params) {
    T object = null;

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      fillPreparedStatement(preparedStatement, params);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        object = resultSetBuilder.build(resultSet);
      }

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return object;
  }

  private static void fillPreparedStatement(PreparedStatement preparedStatement, Object[] params) {

    for (int i = 0; i < params.length; i++) {
      try {
        preparedStatement.setObject(i + 1, params[i]);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
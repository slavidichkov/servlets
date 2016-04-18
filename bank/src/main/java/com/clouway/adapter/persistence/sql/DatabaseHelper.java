package com.clouway.adapter.persistence.sql;

import com.google.common.base.Optional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DatabaseHelper {
  private final DataSource dataSource;

  public DatabaseHelper(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public long executeUpdate(String query, Object... params) throws SQLException {
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

  public <T> Optional<T> fetchOne(String query, RowFetcher<T> rowFetcher, Object... params) throws SQLException {
    List<T> result = fetchList(query, rowFetcher, params);

    if (!result.isEmpty()) {
      return Optional.of(result.get(0));
    }
    return Optional.absent();
  }

  public <T> List<T> fetchList(String query, RowFetcher<T> rowFetcher, Object... params) throws SQLException {
    List<T> list = new ArrayList<T>();
    Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    fillPreparedStatement(preparedStatement, params);
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      T object = rowFetcher.build(resultSet);
      list.add(object);
    }
    preparedStatement.close();
    connection.close();

    return list;
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
package com.clouway.adapter.persistence.sql;

import java.sql.ResultSet;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface ResultSetBuilder<T> {
  T build(ResultSet resultSet);
}

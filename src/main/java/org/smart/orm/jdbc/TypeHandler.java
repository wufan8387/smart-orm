package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface TypeHandler<T> {

  void fill(PreparedStatement ps, int i, T parameter) throws SQLException;

  T transform(ResultSet rs, String columnName) throws SQLException;

  T transform(ResultSet rs, int columnIndex) throws SQLException;

  T transform(CallableStatement cs, int columnIndex) throws SQLException;

}

package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Clinton Begin
 */
public class SqlTimestampHandler extends BaseTypeHandler<Timestamp> {

  @Override
  public void doFill(PreparedStatement ps, int i, Timestamp parameter)
      throws SQLException {
    ps.setTimestamp(i, parameter);
  }

  @Override
  public Timestamp doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getTimestamp(columnName);
  }

  @Override
  public Timestamp doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getTimestamp(columnIndex);
  }

  @Override
  public Timestamp doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getTimestamp(columnIndex);
  }
}

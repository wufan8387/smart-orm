package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * @author Clinton Begin
 */
public class SqlTimeHandler extends BaseTypeHandler<Time> {

  @Override
  public void doFill(PreparedStatement ps, int i, Time parameter)
      throws SQLException {
    ps.setTime(i, parameter);
  }

  @Override
  public Time doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getTime(columnName);
  }

  @Override
  public Time doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getTime(columnIndex);
  }

  @Override
  public Time doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getTime(columnIndex);
  }
}

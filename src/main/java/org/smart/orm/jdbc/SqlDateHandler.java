package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class SqlDateHandler extends BaseTypeHandler<Date> {

  @Override
  public void doFill(PreparedStatement ps, int i, Date parameter)
      throws SQLException {
    ps.setDate(i, parameter);
  }

  @Override
  public Date doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getDate(columnName);
  }

  @Override
  public Date doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getDate(columnIndex);
  }

  @Override
  public Date doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getDate(columnIndex);
  }
}

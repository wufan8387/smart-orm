package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

/**
 * @author Clinton Begin
 */
public class TimeOnlyTypeHandler extends BaseTypeHandler<Date> {

  @Override
  public void doFill(PreparedStatement ps, int i, Date parameter)
      throws SQLException {
    ps.setTime(i, new Time(parameter.getTime()));
  }

  @Override
  public Date doTransform(ResultSet rs, String columnName)
      throws SQLException {
    java.sql.Time sqlTime = rs.getTime(columnName);
    if (sqlTime != null) {
      return new Date(sqlTime.getTime());
    }
    return null;
  }

  @Override
  public Date doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    java.sql.Time sqlTime = rs.getTime(columnIndex);
    if (sqlTime != null) {
      return new Date(sqlTime.getTime());
    }
    return null;
  }

  @Override
  public Date doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    java.sql.Time sqlTime = cs.getTime(columnIndex);
    if (sqlTime != null) {
      return new Date(sqlTime.getTime());
    }
    return null;
  }
}

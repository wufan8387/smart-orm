package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class ObjectHandler extends BaseTypeHandler<Object> {

  @Override
  public void doFill(PreparedStatement ps, int i, Object parameter)
      throws SQLException {
    ps.setObject(i, parameter);
  }

  @Override
  public Object doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getObject(columnName);
  }

  @Override
  public Object doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getObject(columnIndex);
  }

  @Override
  public Object doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getObject(columnIndex);
  }
}

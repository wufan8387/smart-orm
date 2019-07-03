package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class ShortHandler extends BaseTypeHandler<Short> {

  @Override
  public void doFill(PreparedStatement ps, int i, Short parameter)
      throws SQLException {
    ps.setShort(i, parameter);
  }

  @Override
  public Short doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getShort(columnName);
  }

  @Override
  public Short doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getShort(columnIndex);
  }

  @Override
  public Short doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getShort(columnIndex);
  }
}

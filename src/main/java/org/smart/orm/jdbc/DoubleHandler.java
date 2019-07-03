package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class DoubleHandler extends BaseTypeHandler<Double> {

  @Override
  public void doFill(PreparedStatement ps, int i, Double parameter)
      throws SQLException {
    ps.setDouble(i, parameter);
  }

  @Override
  public Double doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getDouble(columnName);
  }

  @Override
  public Double doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getDouble(columnIndex);
  }

  @Override
  public Double doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getDouble(columnIndex);
  }

}

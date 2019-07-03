package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
/**
 * Integer类型处理器
 * 调用PreparedStatement.setInt, ResultSet.getInt, CallableStatement.getInt
 * 
 */
public class IntegerHandler extends BaseTypeHandler<Integer> {

  @Override
  public void doFill(PreparedStatement ps, int i, Integer parameter)
      throws SQLException {
    ps.setInt(i, parameter);
  }

  @Override
  public Integer doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getInt(columnName);
  }

  @Override
  public Integer doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getInt(columnIndex);
  }

  @Override
  public Integer doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getInt(columnIndex);
  }
}

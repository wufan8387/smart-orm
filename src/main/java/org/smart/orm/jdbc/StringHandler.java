package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
/**
 * String类型处理器
 * 调用PreparedStatement.setString, ResultSet.getString, CallableStatement.getString
 * 
 */
public class StringHandler extends BaseTypeHandler<String> {

  @Override
  public void doFill(PreparedStatement ps, int i, String parameter)
      throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String doTransform(ResultSet rs, String columnName)
      throws SQLException {
    return rs.getString(columnName);
  }

  @Override
  public String doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    return rs.getString(columnIndex);
  }

  @Override
  public String doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    return cs.getString(columnIndex);
  }
}

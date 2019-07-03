package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class NStringHandler extends BaseTypeHandler<String> {

  @Override
  public void doFill(PreparedStatement ps, int i, String parameter)
      throws SQLException {
//    ps.setNString(i, ((String) parameter));
    ps.setString(i, parameter);
  }

  @Override
  public String doTransform(ResultSet rs, String columnName)
      throws SQLException {
//    return rs.getNString(columnName);
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
//    return cs.getNString(columnIndex);
    return cs.getString(columnIndex);
  }

}
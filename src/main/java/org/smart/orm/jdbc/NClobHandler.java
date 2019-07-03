package org.smart.orm.jdbc;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class NClobHandler extends BaseTypeHandler<String> {

  @Override
  public void doFill(PreparedStatement ps, int i, String parameter)
      throws SQLException {
    StringReader reader = new StringReader(parameter);
    ps.setCharacterStream(i, reader, parameter.length());
  }

  @Override
  public String doTransform(ResultSet rs, String columnName)
      throws SQLException {
    String value = "";
    Clob clob = rs.getClob(columnName);
    if (clob != null) {
      int size = (int) clob.length();
      value = clob.getSubString(1, size);
    }
    return value;
  }

  @Override
  public String doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    String value = "";
    Clob clob = rs.getClob(columnIndex);
    if (clob != null) {
      int size = (int) clob.length();
      value = clob.getSubString(1, size);
    }
    return value;
  }

  @Override
  public String doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    String value = "";
    Clob clob = cs.getClob(columnIndex);
    if (clob != null) {
      int size = (int) clob.length();
      value = clob.getSubString(1, size);
    }
    return value;
  }
}
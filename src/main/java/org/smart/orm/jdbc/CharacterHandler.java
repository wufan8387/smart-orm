package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class CharacterHandler extends BaseTypeHandler<Character> {

  @Override
  public void doFill(PreparedStatement ps, int i, Character parameter) throws SQLException {
    ps.setString(i, parameter.toString());
  }

  @Override
  public Character doTransform(ResultSet rs, String columnName) throws SQLException {
    String columnValue = rs.getString(columnName);
    if (columnValue != null) {
      return columnValue.charAt(0);
    } else {
      return null;
    }
  }

  @Override
  public Character doTransform(ResultSet rs, int columnIndex) throws SQLException {
    String columnValue = rs.getString(columnIndex);
    if (columnValue != null) {
      return columnValue.charAt(0);
    } else {
      return null;
    }
  }

  @Override
  public Character doTransform(CallableStatement cs, int columnIndex) throws SQLException {
    String columnValue = cs.getString(columnIndex);
    if (columnValue != null) {
      return columnValue.charAt(0);
    } else {
      return null;
    }
  }
}

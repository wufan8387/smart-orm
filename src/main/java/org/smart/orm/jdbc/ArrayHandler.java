package org.smart.orm.jdbc;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class ArrayHandler extends BaseTypeHandler<Object> {



  @Override
  protected void doFill(PreparedStatement ps, int i, Object parameter) throws SQLException {
    ps.setArray(i, (Array) parameter);
  }


  @Override
  protected Object doTransform(ResultSet rs, String columnName) throws SQLException {
    Array array = rs.getArray(columnName);
    return array == null ? null : array.getArray();
  }

  @Override
  protected Object doTransform(ResultSet rs, int columnIndex) throws SQLException {
    Array array = rs.getArray(columnIndex);
    return array == null ? null : array.getArray();
  }

  @Override
  protected Object doTransform(CallableStatement cs, int columnIndex) throws SQLException {
    Array array = cs.getArray(columnIndex);
    return array == null ? null : array.getArray();
  }

}

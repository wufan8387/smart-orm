package org.smart.orm.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.*;

/**
 * @author Clinton Begin
 */
public class BlobByteObjectArrayTypeHandler extends BaseTypeHandler<Byte[]> {

  @Override
  public void doFill(PreparedStatement ps, int i, Byte[] parameter)
      throws SQLException {
    ByteArrayInputStream bis = new ByteArrayInputStream(ByteArrayUtils.convertToPrimitiveArray(parameter));
    ps.setBinaryStream(i, bis, parameter.length);
  }

  @Override
  public Byte[] doTransform(ResultSet rs, String columnName)
      throws SQLException {
    Blob blob = rs.getBlob(columnName);
    return getBytes(blob);
  }

  @Override
  public Byte[] doTransform(ResultSet rs, int columnIndex)
      throws SQLException {
    Blob blob = rs.getBlob(columnIndex);
    return getBytes(blob);
  }

  @Override
  public Byte[] doTransform(CallableStatement cs, int columnIndex)
      throws SQLException {
    Blob blob = cs.getBlob(columnIndex);
    return getBytes(blob);
  }

  private Byte[] getBytes(Blob blob) throws SQLException {
    Byte[] returnValue = null;
    if (blob != null) {
      returnValue = ByteArrayUtils.convertToObjectArray(blob.getBytes(1, (int) blob.length()));
    }
    return returnValue;
  }
}

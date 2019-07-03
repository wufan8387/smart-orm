package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class ByteArrayHandler extends BaseTypeHandler<byte[]> {
    
    @Override
    public void doFill(PreparedStatement ps, int i, byte[] parameter)
            throws SQLException {
        ps.setBytes(i, parameter);
    }
    
    @Override
    public byte[] doTransform(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getBytes(columnName);
    }
    
    @Override
    public byte[] doTransform(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getBytes(columnIndex);
    }
    
    @Override
    public byte[] doTransform(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getBytes(columnIndex);
    }
}

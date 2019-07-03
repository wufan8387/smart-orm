package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ByteHandler extends BaseTypeHandler<Byte> {
    
    @Override
    public void doFill(PreparedStatement ps, int i, Byte parameter)
            throws SQLException {
        ps.setByte(i, parameter);
    }
    
    @Override
    public Byte doTransform(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getByte(columnName);
    }
    
    @Override
    public Byte doTransform(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getByte(columnIndex);
    }
    
    @Override
    public Byte doTransform(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getByte(columnIndex);
    }
}

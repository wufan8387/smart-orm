package org.smart.orm.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class BooleanHandler extends BaseTypeHandler<Boolean> {
    
    @Override
    public void doFill(PreparedStatement ps, int i, Boolean parameter)
            throws SQLException {
        ps.setBoolean(i, parameter);
    }
    
    @Override
    public Boolean doTransform(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getBoolean(columnName);
    }
    
    @Override
    public Boolean doTransform(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getBoolean(columnIndex);
    }
    
    @Override
    public Boolean doTransform(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getBoolean(columnIndex);
    }
}

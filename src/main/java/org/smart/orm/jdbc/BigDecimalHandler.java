package org.smart.orm.jdbc;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class BigDecimalHandler extends BaseTypeHandler<BigDecimal> {
    
    @Override
    public void doFill(PreparedStatement ps, int i, BigDecimal parameter)
            throws SQLException {
        ps.setBigDecimal(i, parameter);
    }
    
    @Override
    public BigDecimal doTransform(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getBigDecimal(columnName);
    }
    
    @Override
    public BigDecimal doTransform(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getBigDecimal(columnIndex);
    }
    
    @Override
    public BigDecimal getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getBigDecimal(columnIndex);
    }
}

package org.smart.orm.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BigIntegerHandler extends BaseTypeHandler<BigInteger> {
    
    @Override
    public void doFill(PreparedStatement ps, int i, BigInteger parameter) throws SQLException {
        ps.setBigDecimal(i, new BigDecimal(parameter));
    }
    
    @Override
    public BigInteger doTransform(ResultSet rs, String columnName) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnName);
        return bigDecimal == null ? null : bigDecimal.toBigInteger();
    }
    
    @Override
    public BigInteger doTransform(ResultSet rs, int columnIndex) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnIndex);
        return bigDecimal == null ? null : bigDecimal.toBigInteger();
    }
    
    @Override
    public BigInteger doTransform(CallableStatement cs, int columnIndex) throws SQLException {
        BigDecimal bigDecimal = cs.getBigDecimal(columnIndex);
        return bigDecimal == null ? null : bigDecimal.toBigInteger();
    }
    
    
}

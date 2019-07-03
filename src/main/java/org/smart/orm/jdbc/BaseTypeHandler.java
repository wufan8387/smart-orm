package org.smart.orm.jdbc;

import java.sql.*;

import org.apache.ibatis.session.Configuration;

public abstract class BaseTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {
    

    @Override
    public void fill(PreparedStatement ps, int i, T parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, Types.NULL);
        } else {
            doFill(ps, i, parameter);
        }
    }
    
    @Override
    public T transform(ResultSet rs, String columnName) throws SQLException {
        T result = doTransform(rs, columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }
    
    @Override
    public T transform(ResultSet rs, int columnIndex) throws SQLException {
        T result = doTransform(rs, columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }
    
    @Override
    public T transform(CallableStatement cs, int columnIndex) throws SQLException {
        T result = doTransform(cs, columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }
    
    protected abstract void doFill(PreparedStatement ps, int i, T parameter) throws SQLException;
    
    protected abstract T doTransform(ResultSet rs, String columnName) throws SQLException;
    
    protected abstract T doTransform(ResultSet rs, int columnIndex) throws SQLException;
    
    protected abstract T doTransform(CallableStatement cs, int columnIndex) throws SQLException;
    
}

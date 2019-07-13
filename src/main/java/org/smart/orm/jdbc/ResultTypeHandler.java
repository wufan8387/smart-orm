package org.smart.orm.jdbc;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ResultTypeHandler {
    
    private final Map<Class<?>, DataGetter<?>> getterMap = new HashMap<>();
    
    
    public ResultTypeHandler() {
        initializeDefaultGetter();
    }
    
    public <T> void register(Class<T> cls, DataGetter<T> getter) {
        getterMap.put(cls, getter);
    }
    
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T handle(Class<T> cls, ResultSet rs, int index) throws SQLException {
        DataGetter<T> getter = (DataGetter<T>) getterMap.get(cls);
        return getter.getValue(rs, index);
    }
    
    
    private <T> T checkNull(ResultSet resultSet, T result) throws SQLException {
        if (resultSet.wasNull()) {
            return null;
        }
        return result;
    }
    
    private <T, K> T checkNull(ResultSet resultSet, K result, Function<K, T> function) throws SQLException {
        if (resultSet.wasNull()) {
            return null;
        }
        return function.apply(result);
    }
    
    private void initializeDefaultGetter() {
        
        register(int.class, ResultSet::getInt);
        register(long.class, ResultSet::getLong);
        register(float.class, ResultSet::getFloat);
        register(double.class, ResultSet::getDouble);
        register(byte.class, ResultSet::getByte);
        register(boolean.class, ResultSet::getBoolean);
        register(short.class, ResultSet::getShort);
        
        register(Integer.class, (resultSet, index) -> checkNull(resultSet, resultSet.getInt(index)));
        register(Long.class, (resultSet, index) -> checkNull(resultSet, resultSet.getLong(index)));
        register(Float.class, (resultSet, index) -> checkNull(resultSet, resultSet.getFloat(index)));
        register(Double.class, (resultSet, index) -> checkNull(resultSet, resultSet.getDouble(index)));
        register(Byte.class, (resultSet, index) -> checkNull(resultSet, resultSet.getByte(index)));
        register(Boolean.class, (resultSet, index) -> checkNull(resultSet, resultSet.getBoolean(index)));
        register(Short.class, (resultSet, index) -> checkNull(resultSet, resultSet.getShort(index)));
        
        
        register(BigDecimal.class, ResultSet::getBigDecimal);
        register(String.class, ResultSet::getString);
        register(Clob.class, ResultSet::getClob);
        register(Blob.class, ResultSet::getBlob);
        register(Object.class, ResultSet::getObject);
        register(Date.class, ResultSet::getDate);
        
        register(java.util.Date.class, ResultSet::getTimestamp);
        register(Time.class, ResultSet::getTime);
        register(Timestamp.class, ResultSet::getTimestamp);
        register(URL.class, ResultSet::getURL);
        
        
        register(BigInteger.class, (resultSet, index) ->
                checkNull(resultSet, resultSet.getLong(index), BigInteger::valueOf)
        );
        
        
        register(Calendar.class, (rs, index) ->
                checkNull(rs, rs.getTimestamp(index), t -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(t.getTime());
                    return cal;
                })
        );
        
        register(Character.class, (rs, index) ->
                checkNull(rs, rs.getString(index), t -> t.charAt(0))
        );
        
        
    }
    
    
}

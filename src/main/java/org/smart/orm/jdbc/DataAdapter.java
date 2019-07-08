package org.smart.orm.jdbc;

import javafx.beans.property.adapter.JavaBeanDoubleProperty;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DataAdapter {
    
    
    private final Map<Class<?>, DataGetter<?>> getterMap = new HashMap<>();
    
    private final Map<Class<?>, DataSetter<?>> setterMap = new HashMap<>();
    
    
    public <T> void registerGetter(Class<T> cls, DataGetter<T> getter) {
        getterMap.put(cls, getter);
    }
    
    
    public <T> void registerSetter(Class<T> cls, DataSetter<T> setter) {
        setterMap.put(cls, setter);
    }
    
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getValue(Class<T> cls, ResultSet rs, int index) throws SQLException {
        DataGetter<T> getter = (DataGetter<T>) getterMap.get(cls);
        return getter.getValue(rs, index);
    }
    
    
    @SuppressWarnings("unchecked")
    public <T> void setValue(PreparedStatement st, int index, T value) throws SQLException {
        DataSetter<T> setter = (DataSetter<T>) setterMap.get(value.getClass());
        setter.setValue(st, index, value);
    }
    
    
    @SuppressWarnings("unchecked")
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
    
    
    private <T> void checkNull(PreparedStatement statement, int index, T value, SetParameter<T>
            consumer) throws SQLException {
        if (value == null) {
            statement.setNull(index, JDBCType.NULL.getVendorTypeNumber());
        } else {
            consumer.set(index, value);
        }
        
    }
    
    @FunctionalInterface
    private interface SetParameter<T> {
        void set(int index, T value) throws SQLException;
    }
    
    
    private void initializeDefaultGetter() {
        
        
        registerGetter(int.class, ResultSet::getInt);
        registerGetter(long.class, ResultSet::getLong);
        registerGetter(float.class, ResultSet::getFloat);
        registerGetter(double.class, ResultSet::getDouble);
        registerGetter(byte.class, ResultSet::getByte);
        registerGetter(boolean.class, ResultSet::getBoolean);
        registerGetter(short.class, ResultSet::getShort);
        
        
        registerGetter(Integer.class, (resultSet, index) -> checkNull(resultSet, resultSet.getInt(index)));
        registerGetter(Long.class, (resultSet, index) -> checkNull(resultSet, resultSet.getLong(index)));
        registerGetter(Float.class, (resultSet, index) -> checkNull(resultSet, resultSet.getFloat(index)));
        registerGetter(Double.class, (resultSet, index) -> checkNull(resultSet, resultSet.getDouble(index)));
        registerGetter(Byte.class, (resultSet, index) -> checkNull(resultSet, resultSet.getByte(index)));
        registerGetter(Boolean.class, (resultSet, index) -> checkNull(resultSet, resultSet.getBoolean(index)));
        registerGetter(Short.class, (resultSet, index) -> checkNull(resultSet, resultSet.getShort(index)));
        
        
        registerGetter(BigDecimal.class, ResultSet::getBigDecimal);
        registerGetter(String.class, ResultSet::getString);
        registerGetter(Clob.class, ResultSet::getClob);
        registerGetter(Blob.class, ResultSet::getBlob);
        registerGetter(Object.class, ResultSet::getObject);
        registerGetter(Date.class, ResultSet::getDate);
        
        registerGetter(java.util.Date.class, ResultSet::getTimestamp);
        registerGetter(Time.class, ResultSet::getTime);
        registerGetter(Timestamp.class, ResultSet::getTimestamp);
        registerGetter(URL.class, ResultSet::getURL);
        
        
        registerGetter(BigInteger.class, (resultSet, index) -> {
            return checkNull(resultSet, resultSet.getLong(index), BigInteger::valueOf);
        });
        
        
        registerGetter(Calendar.class, (rs, index) -> {
            return checkNull(rs, rs.getTimestamp(index), t -> {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(t.getTime());
                return cal;
            });
        });
        
        registerGetter(Character.class, (rs, index) -> {
            return checkNull(rs, rs.getString(index), t -> t.charAt(0));
        });
        
        
    }
    
    private void inisitlaizeDefaultSetter() {
        registerSetter(int.class, PreparedStatement::setInt);
        registerSetter(long.class, PreparedStatement::setLong);
        registerSetter(float.class, PreparedStatement::setFloat);
        registerSetter(double.class, PreparedStatement::setDouble);
        registerSetter(byte.class, PreparedStatement::setByte);
        registerSetter(boolean.class, PreparedStatement::setBoolean);
        registerSetter(short.class, PreparedStatement::setShort);
        
        registerSetter(BigDecimal.class, PreparedStatement::setBigDecimal);
        registerSetter(String.class, PreparedStatement::setString);
        registerSetter(Clob.class, PreparedStatement::setClob);
        registerSetter(Blob.class, PreparedStatement::setBlob);
        registerSetter(Object.class, PreparedStatement::setObject);
        registerSetter(Date.class, PreparedStatement::setDate);
        
        registerSetter(Time.class, PreparedStatement::setTime);
        registerSetter(Timestamp.class, PreparedStatement::setTimestamp);
        registerSetter(URL.class, PreparedStatement::setURL);
        registerSetter(Object.class, PreparedStatement::setObject);
        
        
        registerSetter(Integer.class, (st, index, value) -> checkNull(st, index, value, st::setInt));
        
        registerSetter(Long.class, (st, index, value) -> checkNull(st, index, value, st::setLong));
        registerSetter(Float.class, (st, index, value) -> checkNull(st, index, value, st::setFloat));
        registerSetter(Double.class, (st, index, value) -> checkNull(st, index, value, st::setDouble));
        registerSetter(Byte.class, (st, index, value) -> checkNull(st, index, value, st::setByte));
        registerSetter(Boolean.class, (st, index, value) -> checkNull(st, index, value, st::setBoolean));
        registerSetter(Short.class, (st, index, value) -> checkNull(st, index, value, st::setShort));
        
        
        registerSetter(java.util.Date.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setTimestamp(t, new Timestamp(v.getTime())));
        });
        
        
        registerSetter(BigInteger.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setLong(t, v.longValue()));
        });
        
        
        registerSetter(Calendar.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setTimestamp(t, new Timestamp(v.getTimeInMillis())));
        });
        
        registerSetter(Character.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setString(t, v.toString()));
        });
        
    }
    
    
}

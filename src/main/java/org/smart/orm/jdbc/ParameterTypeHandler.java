package org.smart.orm.jdbc;

import org.smart.orm.functions.ParameterSetter;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ParameterTypeHandler {
    
    private final Map<Class<?>, ParameterSetter<?>> setterMap = new HashMap<>();
    
    public ParameterTypeHandler() {
        initializeDefaultSetter();
    }
    
    public <T> void register(Class<T> cls, ParameterSetter<T> setter) {
        setterMap.put(cls, setter);
    }
    
    @SuppressWarnings("unchecked")
    public <T> void handle(PreparedStatement st, int index, T value) throws SQLException {
        Class<?> cls = value.getClass();
        ParameterSetter<T> setter = (ParameterSetter<T>) setterMap.get(cls);
        setter.set(st, index, value);
    }
    
    private <T> void checkNull(PreparedStatement statement, int index, T value, SetParameter<T>
            consumer) throws SQLException {
        if (value == null) {
            statement.setNull(index, JDBCType.NULL.getVendorTypeNumber());
        } else {
            consumer.set(index, value);
        }
        
    }
    
    
    private static byte[] convertToPrimitiveArray(Byte[] objects) {
        final byte[] bytes = new byte[objects.length];
        for (int i = 0; i < objects.length; i++) {
            bytes[i] = objects[i];
        }
        return bytes;
    }
    
    private void initializeDefaultSetter() {
        register(int.class, PreparedStatement::setInt);
        register(long.class, PreparedStatement::setLong);
        register(float.class, PreparedStatement::setFloat);
        register(double.class, PreparedStatement::setDouble);
        register(byte.class, PreparedStatement::setByte);
        register(boolean.class, PreparedStatement::setBoolean);
        register(short.class, PreparedStatement::setShort);
        
        register(BigDecimal.class, PreparedStatement::setBigDecimal);
        register(String.class, PreparedStatement::setString);
        register(Clob.class, PreparedStatement::setClob);
        register(Blob.class, PreparedStatement::setBlob);
        register(Object.class, PreparedStatement::setObject);
        register(Date.class, PreparedStatement::setDate);
        
        register(Time.class, PreparedStatement::setTime);
        register(Timestamp.class, PreparedStatement::setTimestamp);
        register(URL.class, PreparedStatement::setURL);
        register(Object.class, PreparedStatement::setObject);
        
        register(byte[].class, PreparedStatement::setBytes);
        
        
        register(Integer.class, (st, index, value) -> checkNull(st, index, value, st::setInt));
        
        register(Long.class, (st, index, value) -> checkNull(st, index, value, st::setLong));
        register(Float.class, (st, index, value) -> checkNull(st, index, value, st::setFloat));
        register(Double.class, (st, index, value) -> checkNull(st, index, value, st::setDouble));
        register(Byte.class, (st, index, value) -> checkNull(st, index, value, st::setByte));
        register(Boolean.class, (st, index, value) -> checkNull(st, index, value, st::setBoolean));
        register(Short.class, (st, index, value) -> checkNull(st, index, value, st::setShort));
        
        
        register(java.util.Date.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setTimestamp(t, new Timestamp(v.getTime())));
        });
        
        
        register(BigInteger.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setLong(t, v.longValue()));
        });
        
        
        register(Calendar.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setTimestamp(t, new Timestamp(v.getTimeInMillis())));
        });
        
        register(Character.class, (st, index, value) -> {
            checkNull(st, index, value, (t, v) -> st.setString(t, v.toString()));
        });
        
        register(Byte[].class, (st, index, value) -> {
            st.setBinaryStream(index, new ByteArrayInputStream(convertToPrimitiveArray(value)), value.length);
        });
    
 
    }
    
    

    
}

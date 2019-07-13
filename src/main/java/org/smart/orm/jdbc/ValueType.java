//package org.smart.orm.jdbc;
//
//import org.joda.time.DateTime;
//
//import javax.annotation.Nullable;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.net.URL;
//import java.sql.*;
//import java.util.Calendar;
//
//public class ValueType {
//
//
//    public DataSetter<BigDecimal> setBigDecimalToDouble() {
//        return (statement, index, value) -> statement.setDouble(index, value.doubleValue());
//    }
//
//
//    public DataSetter<BigInteger> setBigInteger() {
//        return (statement, index, value) -> statement.setBigDecimal(index, new BigDecimal(value));
//    }
//
//
//    public DataSetter<BigInteger> setBigIntegerToLong() {
//        return (statement, index, value) -> statement.setLong(index, value.longValue());
//    }
//
//
//    public <T extends Enum<T>> DataGetter<T> getEnumFromInt(Class<T> cls) {
//        return (resultSet, index) -> {
//            int value = resultSet.getInt(index);
//            return resultSet.wasNull() ? null : cls.getEnumConstants()[value];
//
//        };
//    }
//
//    public <T extends Enum<T>> DataSetter<T> setEnumToInt() {
//        return (statement, index, value) -> {
//            statement.setInt(index, value.ordinal());
//        };
//    }
//
//
//    public <T extends Enum<T>> DataGetter<T> getEnumFromString(Class<T> cls) {
//        return (resultSet, index) -> {
//            String value = resultSet.getString(index);
//            return resultSet.wasNull() ? null : Enum.valueOf(cls, value);
//
//        };
//    }
//
//    public <T extends Enum<T>> DataSetter<T> setEnumToString() {
//        return (statement, index, value) -> {
//            statement.setString(index, value.name());
//        };
//    }
//
//
//}

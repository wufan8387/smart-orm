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
//        return (statement, index, item2) -> statement.setDouble(index, item2.doubleValue());
//    }
//
//
//    public DataSetter<BigInteger> setBigInteger() {
//        return (statement, index, item2) -> statement.setBigDecimal(index, new BigDecimal(item2));
//    }
//
//
//    public DataSetter<BigInteger> setBigIntegerToLong() {
//        return (statement, index, item2) -> statement.setLong(index, item2.longValue());
//    }
//
//
//    public <T extends Enum<T>> DataGetter<T> getEnumFromInt(Class<T> cls) {
//        return (resultSet, index) -> {
//            int item2 = resultSet.getInt(index);
//            return resultSet.wasNull() ? null : cls.getEnumConstants()[item2];
//
//        };
//    }
//
//    public <T extends Enum<T>> DataSetter<T> setEnumToInt() {
//        return (statement, index, item2) -> {
//            statement.setInt(index, item2.ordinal());
//        };
//    }
//
//
//    public <T extends Enum<T>> DataGetter<T> getEnumFromString(Class<T> cls) {
//        return (resultSet, index) -> {
//            String item2 = resultSet.getString(index);
//            return resultSet.wasNull() ? null : Enum.valueOf(cls, item2);
//
//        };
//    }
//
//    public <T extends Enum<T>> DataSetter<T> setEnumToString() {
//        return (statement, index, item2) -> {
//            statement.setString(index, item2.name());
//        };
//    }
//
//
//}

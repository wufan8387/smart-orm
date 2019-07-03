package org.smart.orm.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.JDBCType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConverterFactory {

    private static Map<JDBCType, Class> jdbcMap = new HashMap<>();

    private static Map<Class, JDBCType> classMap = new HashMap<>();


    static {


        classMap.put(Boolean.class, JDBCType.BIT);
        classMap.put(boolean.class, JDBCType.BIT);
        jdbcMap.put(JDBCType.BOOLEAN, boolean.class);
        jdbcMap.put(JDBCType.BIT, boolean.class);

        classMap.put(Byte.class, JDBCType.TINYINT);
        classMap.put(byte.class, JDBCType.TINYINT);
        jdbcMap.put(JDBCType.TINYINT, byte.class);

        classMap.put(Short.class, JDBCType.SMALLINT);
        classMap.put(short.class, JDBCType.SMALLINT);
        jdbcMap.put(JDBCType.SMALLINT, short.class);

        classMap.put(Integer.class, JDBCType.INTEGER);
        classMap.put(int.class, JDBCType.INTEGER);
        jdbcMap.put(JDBCType.INTEGER, int.class);

        classMap.put(Long.class, new JDBCType.BIGINT());
        classMap.put(long.class, new JDBCType.BIGINT());
        jdbcMap.put(JDBCType.BIGINT, float.class);

        classMap.put(Float.class, JDBCType.FLOAT);
        classMap.put(float.class, JDBCType.FLOAT);
        jdbcMap.put(JDBCType.FLOAT, float.class);

        classMap.put(Double.class, JDBCType.DOUBLE);
        classMap.put(double.class, JDBCType.DOUBLE);
        jdbcMap.put(JDBCType.DOUBLE, double.class);

        //以下是为同一个类型的多种变种注册到多个不同的handler
        classMap.put(String.class, new StringTypeHandler());
        classMap.put(String.class, JDBCType.CHAR, new StringTypeHandler());
        classMap.put(String.class, JDBCType.CLOB, new ClobTypeHandler());
        classMap.put(String.class, JDBCType.VARCHAR, new StringTypeHandler());
        classMap.put(String.class, JDBCType.LONGVARCHAR, new ClobTypeHandler());
        classMap.put(String.class, JDBCType.NVARCHAR, new NStringTypeHandler());
        classMap.put(String.class, JDBCType.NCHAR, new NStringTypeHandler());
        classMap.put(String.class, JDBCType.NCLOB, new NClobTypeHandler());
        jdbcMap.put(JDBCType.CHAR, String.class);
        jdbcMap.put(JDBCType.VARCHAR, String.class);
        jdbcMap.put(JDBCType.CLOB, String.class);
        jdbcMap.put(JDBCType.LONGVARCHAR, String.class);
        jdbcMap.put(JDBCType.NVARCHAR, String.class);
        jdbcMap.put(JDBCType.NCHAR, String.class);
        jdbcMap.put(JDBCType.NCLOB, String.class);

        classMap.put(Object.class, JDBCType.ARRAY);
        jdbcMap.put(JDBCType.ARRAY, Object.class);


        classMap.put(BigInteger.class, JDBCType.BIGINT);
        jdbcMap.put(JDBCType.BIGINT, long.class);

        classMap.put(BigDecimal.class, JDBCType.DECIMAL);
        jdbcMap.put(JDBCType.REAL, BigDecimal.class);
        jdbcMap.put(JDBCType.DECIMAL, BigDecimal.class);
        jdbcMap.put(JDBCType.NUMERIC, BigDecimal.class);

        classMap.put(Byte[].class, new ByteObjectArrayTypeHandler());
        classMap.put(Byte[].class, JDBCType.BLOB, new BlobByteObjectArrayTypeHandler());
        classMap.put(Byte[].class, JDBCType.LONGVARBINARY, new BlobByteObjectArrayTypeHandler());
        classMap.put(byte[].class, new ByteArrayTypeHandler());
        classMap.put(byte[].class, JDBCType.BLOB, new BlobTypeHandler());
        classMap.put(byte[].class, JDBCType.LONGVARBINARY, new BlobTypeHandler());
        jdbcMap.put(JDBCType.LONGVARBINARY, byte[].class);
        jdbcMap.put(JDBCType.BLOB, byte[].class);

        classMap.put(Object.class, UNKNOWN_TYPE_HANDLER);
        classMap.put(Object.class, JDBCType.OTHER, UNKNOWN_TYPE_HANDLER);
        classMap.put(JDBCType.OTHER, UNKNOWN_TYPE_HANDLER);

        classMap.put(Date.class, new DateTypeHandler());
        classMap.put(Date.class, JDBCType.DATE, new DateOnlyTypeHandler());
        classMap.put(Date.class, JDBCType.TIME, new TimeOnlyTypeHandler());
        classMap.put(JDBCType.TIMESTAMP, Date.class);
        classMap.put(JDBCType.DATE, Date.class);
        classMap.put(JDBCType.TIME, Date.class);

        classMap.put(java.sql.Date.class, new SqlDateTypeHandler());
        classMap.put(java.sql.Time.class, new SqlTimeTypeHandler());
        classMap.put(java.sql.Timestamp.class, new SqlTimestampTypeHandler());

        // issue #273
        classMap.put(Character.class, new CharacterTypeHandler());
        classMap.put(char.class, new CharacterTypeHandler());


    }


}

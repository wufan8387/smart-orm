//package org.smart.orm.jdbc;
//
//import java.io.ByteArrayInputStream;
//import java.sql.*;
//
//import javax.annotation.Nullable;
//
//import com.google.common.primitives.Primitives;
//
//public class ArrayType {
//
//    static byte[] convertToPrimitiveArray(Byte[] objects) {
//        final byte[] bytes = new byte[objects.length];
//        for (int i = 0; i < objects.length; i++) {
//            bytes[i] = objects[i].byteValue();
//        }
//        return bytes;
//    }
//
//    static Byte[] convertToObjectArray(byte[] bytes) {
//        final Byte[] objects = new Byte[bytes.length];
//        for (int i = 0; i < bytes.length; i++) {
//            objects[i] = Byte.valueOf(bytes[i]);
//        }
//        return objects;
//    }
//
//    public DataGetter<byte[]> byteGetter() {
//        return new DataGetter<byte[]>() {
//
//            @SuppressWarnings("unchecked")
//            @Nullable
//            @Override
//            public byte[] getValue(ResultSet resultSet, int index) throws SQLException {
//                return resultSet.getBytes(index);
//
//            }
//
//        };
//    }
//
//    public DataSetter<byte[]> byteSetter() {
//        return new DataSetter<byte[]>() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public void setValue(PreparedStatement statement, int index, byte[] value) throws SQLException {
//                statement.setBytes(index, value);
//            }
//        };
//    }
//
//
//    public DataGetter<Byte[]> blobGetter() {
//        return new DataGetter<Byte[]>() {
//
//            @SuppressWarnings("unchecked")
//            @Nullable
//            @Override
//            public Byte[] getValue(ResultSet resultSet, int index) throws SQLException {
//                Blob blob = resultSet.getBlob(index);
//                Byte[] result = null;
//                if (blob != null) {
//                    result = convertToObjectArray(blob.getBytes(1, (int) blob.length()));
//                }
//                return  result;
//            }
//
//        };
//    }
//
//    public DataSetter<Byte[]> blobSetter() {
//        return new DataSetter<Byte[]>() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void setValue(PreparedStatement statement, int index, Byte[] value) throws SQLException {
//                ByteArrayInputStream bis = new ByteArrayInputStream(convertToPrimitiveArray(value));
//                statement.setBinaryStream(index, bis, value.length);
//            }
//
//        };
//    }
//
//
//
//
//}

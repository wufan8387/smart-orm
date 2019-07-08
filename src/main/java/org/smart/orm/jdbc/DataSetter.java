package org.smart.orm.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface DataSetter<T> {
    void setValue(PreparedStatement statement, int index, T value) throws SQLException;
}
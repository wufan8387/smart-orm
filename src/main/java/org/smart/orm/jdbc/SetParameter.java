package org.smart.orm.jdbc;

import java.sql.SQLException;

@FunctionalInterface
public interface SetParameter<T> {
    void set(int index, T value) throws SQLException;
}

package org.smart.orm.functions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterSetter<T> {
    void set(PreparedStatement statement, int index, T value) throws SQLException;
}
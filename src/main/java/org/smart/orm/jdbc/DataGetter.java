package org.smart.orm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface DataGetter<T> {
    
    T getValue(ResultSet resultSet, int index) throws SQLException;
}

package org.smart.orm.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultGetter<T> {
    
    T get(ResultSet resultSet, int index) throws SQLException;
}

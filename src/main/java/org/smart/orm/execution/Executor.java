package org.smart.orm.execution;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;

public interface Executor {
    
    Connection getConnection();
    
    void setConnection(Connection connection);
    
    <T> void executeQuery(String sql, ResultHandler<T> handler, Object... args);
    
    CachedRowSet executeQuery(String sql, Object... args);
    
    <T> int insert(String sql, ResultHandler<T> handler, int autoGeneratedKeys, Object... args);
    
    int update(String sql, Object... args);
    
    int delete(String sql, Object... args);
    
    <T> void execute(String sql, ResultHandler<T> handler);
    
}

package org.smart.orm.execution;


import java.sql.ResultSet;

public interface ResultHandler<T> {
    
    void handle(ResultSet resultset);
    
    void addListener(ResultListener<T> listener);
    
}

package org.smart.orm.execution;


import java.sql.ResultSet;
import java.util.List;

public interface ResultHandler<T> {
    
    void handle(ResultSet resultset);
    
    T getFirst();
    
    List<T> getAll();
    
}

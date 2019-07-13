package org.smart.orm.execution;

@FunctionalInterface
public interface ResultListener<T> {
    
    void handle(T data);
    
}

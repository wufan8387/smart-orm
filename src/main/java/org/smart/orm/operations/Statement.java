package org.smart.orm.operations;


import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Statement {
    
    UUID getId();
    
    List<Object> getParams();
    
    void addParam(Object param);
    
    
    String alias(String term);
    
    <T extends SqlNode<?>> T attach(T node);
    
    <T extends SqlNode<?>> List<T> find(int nodeType, Predicate<T> predicate);
    
    <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate);
    
    <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other);
    
}

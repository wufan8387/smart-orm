package org.smart.orm.operations;


import java.util.function.Supplier;

public interface SqlNode<T extends Statement, K extends SqlNode<T, K>> {
    
    int getType();
    
    T statement();
    
    K attach(T statement);
    
    Supplier<Object[]> getParams();
    
    void toString(StringBuilder sb);
    
}

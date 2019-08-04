package org.smart.orm.operations;

public interface SqlNode<T extends Statement, K extends SqlNode<T, K>> {
    
    int getType();
    
    T statement();
    
    K attach(T statement);
    
    Object[] getParams();
    
    void toString(StringBuilder sb);
    
}

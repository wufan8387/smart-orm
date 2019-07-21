package org.smart.orm.operations;

public interface SqlNode<T extends Statement> {
    
    int getType();
    
    T statement();
    
    void toString(StringBuilder sb);
    
}

package org.smart.orm.operations;

public interface SqlNode {
    
    int getType();
    
    void add(SqlNode node);
    
    
}

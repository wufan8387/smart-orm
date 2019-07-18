package org.smart.orm.operations.text;

import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

public class AttributeNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    @Override
    public int getType() {
        return 0;
    }
    
    @Override
    public T getStatement() {
        return statement;
    }
    
    @Override
    public void add(SqlNode node) {
    
    }
    
    
}

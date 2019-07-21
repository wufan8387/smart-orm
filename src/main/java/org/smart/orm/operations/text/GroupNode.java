package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

public class GroupNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    
    public GroupNode(T statement) {
        this.statement = statement;
        statement.attach(this);
    }
    
    @Override
    public int getType() {
        return NodeType.GROUP;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    @Override
    public void toString(StringBuilder sb) {
    
    }
}

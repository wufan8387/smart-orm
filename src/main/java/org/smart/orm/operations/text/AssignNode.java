package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

public class AssignNode<T extends Statement> extends AbstractSqlNode<T, AssignNode<T>> {
    
    
    private Object value;
    
    private String attr;
    
    public AssignNode(String attr, Object value) {
        this.value = value;
        this.attr = attr;
    }
    
    @Override
    public int getType() {
        return NodeType.ASSIGN;
    }
    
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Token.ASSIGN.apply(attr, "?"));
        statement().getParams().add(value);
        
    }
}

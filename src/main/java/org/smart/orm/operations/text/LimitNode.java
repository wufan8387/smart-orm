package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

public class LimitNode<T extends Statement> extends AbstractSqlNode<T, LimitNode<T>> {
    
 
    private Integer start;
    
    private Integer end;
   
    public Integer getStart() {
        return start;
    }
    
    public LimitNode<T> setStart(Integer start) {
        this.start = start;
        return this;
    }
    
    public Integer getEnd() {
        return end;
    }
    
    public LimitNode<T> setEnd(Integer end) {
        this.end = end;
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.LIMIT;
    }
    
 
    @Override
    public void toString(StringBuilder sb) {
        sb.append(Token.LIMIT.apply(start, end));
    }
}

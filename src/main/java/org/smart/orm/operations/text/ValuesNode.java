package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Statement;


public class ValuesNode<T extends Statement> extends AbstractSqlNode<T, ValuesNode<T>> {
    
    
    public ValuesNode(Object[] params) {
        setParams(params);
    }
    
    
    @Override
    public int getType() {
        return NodeType.VALUES;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append("(");
        Object[] params = getParams();
        T statement = statement();
        int len = params.length;
        for (int i = 0; i < len; i++) {
            sb.append("?");
            statement.getParams().add(params[i]);
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        
        
    }
}

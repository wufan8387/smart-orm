package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ValuesNode<T extends Statement> implements SqlNode<T> {
    
    
    private T statement;
    
    private Object[] params;
    
    public ValuesNode(T statement,Object[] params) {
        this.statement = statement;
        this.params = params;
    }
    
    
    public Object[] getParams() {
        return params;
    }
    
    @Override
    public int getType() {
        return NodeType.VALUES;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append("(");
        
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

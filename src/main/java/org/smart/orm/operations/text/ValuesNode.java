package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ValuesNode<T extends Statement> implements SqlNode<T> {
    
    
    private T statement;
    
    private final List<Object> valueList = new ArrayList<>();
    
    public ValuesNode(T statement) {
        this.statement = statement;
        statement.attach(this);
    }
    
    public List<Object> getValueList() {
        return valueList;
    }
    
    public ValuesNode<T> addValue(Object... value) {
        this.valueList.addAll(Arrays.asList(value));
        return this;
    }
    
    
    public ValuesNode<T> values(Object... value) {
        return new ValuesNode<>(statement).addValue(value);
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
        
        int len = valueList.size();
        for (int i = 0; i < len; i++) {
            sb.append("?");
            statement.addParam(valueList.get(i));
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        
        
    }
}

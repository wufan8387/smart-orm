package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

import java.util.ArrayList;
import java.util.List;

public class SetNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    private final List<String> attrList = new ArrayList<>();
    
    private final List<Object> valueList = new ArrayList<>();
    
    
    public SetNode(T statement) {
        this.statement = statement;
    }
    
    @Override
    public int getType() {
        return NodeType.SET;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    public SetNode<T> assign(String attr, Object value) {
        int index = attrList.indexOf(attr);
        if (index < 0) {
            attrList.add(attr);
            valueList.add(value);
        } else {
            valueList.set(index, value);
        }
        return this;
    }
    
    
    public SetNode<T> remove(String attr) {
        int index = attrList.indexOf(attr);
        if (index >= 0) {
            attrList.remove(index);
            valueList.remove(index);
        }
        return this;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        int len = attrList.size();
        
        if (len == 0)
            return;
        
        for (int i = 0; i < len; i++) {
            sb.append(Token.ASSIGN.apply(attrList.get(i), "?"));
            statement.addParam(valueList.get(i));
            if (i < len - 1)
                sb.append(",");
        }
        
    }
}

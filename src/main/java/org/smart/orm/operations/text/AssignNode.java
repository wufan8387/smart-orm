package org.smart.orm.operations.text;

import org.smart.orm.data.KeyValuePair;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

import java.util.ArrayList;
import java.util.List;

public class AssignNode<T extends Statement> extends AbstractSqlNode<T, AssignNode<T>> {
    
    
    private final List<KeyValuePair<String, Object>> kvList = new ArrayList<>();
    
    @Override
    public int getType() {
        return NodeType.ASSIGN;
    }
    
    
    public AssignNode<T> assign(String attr, Object value) {
        kvList.removeIf(t -> t.getKey().equals(attr));
        kvList.add(new KeyValuePair<>(attr, value));
        return this;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        int len = kvList.size();
        
        if (len == 0)
            return;
        
        T statement = statement();
        for (int i = 0; i < len; i++) {
            KeyValuePair<String, Object> kv = kvList.get(i);
            sb.append(Token.ASSIGN.apply(kv.getKey(), "?"));
            statement.getParams().add(kv.getValue());
            if (i < len - 1)
                sb.append(",");
        }
        
    }
}

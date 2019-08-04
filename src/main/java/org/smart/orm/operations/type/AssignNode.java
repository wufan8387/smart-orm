package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;

public class AssignNode<T extends Statement, K extends Model<K>> extends AbstractSqlNode<T, AssignNode<T, K>> {
    
    private Object value;
    
    private PropertyInfo prop;
    
    public AssignNode(RelationNode<T, K> rel, PropertyGetter<K> attr, Object value) {
        this.value = value;
        prop = rel.getEntityInfo().getProp(attr);
    }
    
    @Override
    public int getType() {
        return NodeType.ASSIGN;
    }
    
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Token.ASSIGN.apply(prop.getColumnName(), "?"));
        statement().getParams().add(value);
        
    }
    
    
}

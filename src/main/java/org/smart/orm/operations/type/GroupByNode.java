package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;

import java.util.ArrayList;
import java.util.List;

public class GroupByNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    private List<GroupByInfo<T, ?>> groupByList = new ArrayList<>();
    
    
    public GroupByNode(T statement) {
        this.statement = statement;
        statement.attach(this);
    }
    
    public <K extends Model<K>> GroupByNode<T> add(Class<K> rel, PropertyGetter<K> attr) {
        RelationNode<T, K> relNode = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(rel).getTable().getName()));
        GroupByInfo<T, K> orderByInfo = new GroupByInfo<>();
        orderByInfo.attr = attr;
        orderByInfo.rel = relNode;
        groupByList.add(orderByInfo);
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.GROUP_BY;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        
        int len = groupByList.size();
        
        if (len == 0)
            return;
        
        sb.append(Token.GROUP_BY);
        
        for (int i = 0; i < len; i++) {
            GroupByInfo<T, ?> item = groupByList.get(i);
            String attr = LambdaParser.getGetter(item.attr).getName();
            sb.append(Token.ATTR.apply(item.rel.getAlias(), attr));
            if (i < len - 1)
                sb.append(", ");
            
        }
        
    }
    
    private final static class GroupByInfo<T extends Statement, K extends Model<K>> {
        PropertyGetter<K> attr;
        RelationNode<T, K> rel;
    }
    
    
}

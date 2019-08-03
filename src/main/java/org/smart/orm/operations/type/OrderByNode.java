package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.OrderbyType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.functions.PropertyGetter;

import java.util.ArrayList;
import java.util.List;

public class OrderByNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    private List<OrderByInfo<T, ?>> orderByList = new ArrayList<>();
    
    public OrderByNode(T statement) {
        this.statement = statement;
        statement.attach(this);
    }
    
    public <K extends Model<K>> OrderByNode<T> asc(Class<K> rel, PropertyGetter<K> attr) {
        
        RelationNode<T, K> relNode = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(rel).getTable().getName()));
        
        OrderByInfo<T, K> orderByInfo = new OrderByInfo<>();
        orderByInfo.type = OrderbyType.ASC;
        orderByInfo.rel = relNode;
        orderByInfo.attr = attr;
        orderByList.add(orderByInfo);
        
        return this;
    }
    
    public <K extends Model<K>> OrderByNode<T> desc(Class<K> rel, PropertyGetter<K> attr) {
        
        RelationNode<T, K> relNode = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(rel).getTable().getName()));
        
        OrderByInfo<T, K> orderByInfo = new OrderByInfo<>();
        orderByInfo.type = OrderbyType.DESC;
        orderByInfo.attr = attr;
        orderByInfo.rel = relNode;
        orderByList.add(orderByInfo);
        
        return this;
    }
    
    
    @Override
    public int getType() {
        return NodeType.ORDER_BY;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        int len = orderByList.size();
        
        if (len == 0)
            return;
        
        sb.append(Token.ORDER_BY);
        
        
        for (int i = 0; i < len; i++) {
            OrderByInfo<T, ?> item = orderByList.get(i);
            String attr = Model
                    .getMeta(item.rel.getEntityInfo().getEntityClass())
                    .getPropInfo(item.attr)
                    .getColumnName();
            switch (item.type) {
                case ASC:
                    sb.append(Token.ASC.apply(item.rel.getAlias(), attr));
                    break;
                case DESC:
                    sb.append(Token.DESC.apply(item.rel.getAlias(), attr));
                    break;
            }
            if (i < len - 1)
                sb.append(", ");
            
        }
        
    }
    
    private final static class OrderByInfo<T extends Statement, K extends Model<K>> {
        PropertyGetter<K> attr;
        OrderbyType type;
        RelationNode<T, K> rel;
    }
    
    
}

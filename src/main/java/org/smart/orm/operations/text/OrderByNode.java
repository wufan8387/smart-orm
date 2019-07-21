package org.smart.orm.operations.text;

import antlr.TokenStreamRewriteEngine;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.OrderbyType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

import java.util.ArrayList;
import java.util.List;

public class OrderByNode<T extends Statement> implements SqlNode<T> {
    
    private final static String EXPRESSION_ORDERBY = " ORDER BY %s ";
    
    private final static String EXPRESSION_DESC = " DESC ";
    private final static String EXPRESSION_ASC = " ASC ";
    
    private T statement;
    
    private List<OrderByInfo<T>> orderbyList = new ArrayList<>();
    
    public OrderByNode(T statement) {
        this.statement = statement;
        statement.attach(this);
    }
    
    public final OrderByNode<T> asc(String rel, String attr) {
        
        RelationNode<T> relNode = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rel));
        
        OrderByInfo<T> orderByInfo = new OrderByInfo();
        orderByInfo.type = OrderbyType.ASC;
        orderByInfo.rel = relNode;
        orderByInfo.attr = attr;
        orderbyList.add(orderByInfo);
        
        return this;
    }
    
    public final OrderByNode<T> desc(String rel, String attr) {
        
        RelationNode<T> relNode = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rel));
        
        OrderByInfo orderByInfo = new OrderByInfo();
        orderByInfo.type = OrderbyType.DESC;
        orderByInfo.attr = attr;
        orderByInfo.rel = relNode;
        orderbyList.add(orderByInfo);
        
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
        
        int len = orderbyList.size();
        for (int i = 0; i < len; i++) {
            OrderByInfo<T> item = orderbyList.get(i);
            switch (item.type) {
                case ASC:
                    sb.append(Token.ASC.apply(item.rel.getAlias(), item.attr));
                    break;
                case DESC:
                    sb.append(Token.DESC.apply(item.rel.getAlias(), item.attr));
                    break;
            }
            if (i < len - 1)
                sb.append(", ");
            
        }
        
    }
    
    private final static class OrderByInfo<T extends Statement> {
        String attr;
        OrderbyType type;
        RelationNode<T> rel;
    }
    
    
}

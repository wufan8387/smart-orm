package org.smart.orm.operations.text;


import org.smart.orm.Func;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class UpdateStatement extends AbstractStatement {
    
    
    private ConditionNode<UpdateStatement> whereRoot;
    
    private ConditionNode<UpdateStatement> whereLast;
    
    private LimitNode<UpdateStatement> limitRoot;
    
    private OrderByNode<UpdateStatement> orderByRoot;
    
    private final RelationNode<UpdateStatement> relRoot;
    
    private SetNode<UpdateStatement> setRoot;
    
    
    public UpdateStatement(String rel) {
        relRoot = new RelationNode<>(this, rel);
    }
    
    
    public UpdateStatement(String rel, String alias) {
        relRoot = new RelationNode<>(this, rel).setAlias(alias);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T attach(T node) {
        
        switch (node.getType()) {
            case NodeType.CONDITION:
                ConditionNode<UpdateStatement> whereNode = (ConditionNode<UpdateStatement>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.LIMIT:
                LimitNode<UpdateStatement> limitNode = (LimitNode<UpdateStatement>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<UpdateStatement> orderByNode = (OrderByNode<UpdateStatement>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            
        }
        
        return node;
    }
    
    
    public ConditionNode<UpdateStatement> where(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast);
        } else {
            return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    public ConditionNode<UpdateStatement> where(String rel, String attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, rel, attr, op, this.whereLast, params);
        } else {
            return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    
    public ConditionNode<UpdateStatement> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<UpdateStatement> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<UpdateStatement> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public ConditionNode<UpdateStatement> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public LimitNode<UpdateStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public UpdateStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public OrderByNode<UpdateStatement> orderBy(String rel, String attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public OrderByNode<UpdateStatement> orderByDesc(String rel, String attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    public SetNode<UpdateStatement> set(String attr, Object value) {
        if (setRoot == null)
            setRoot = new SetNode<>(this);
        setRoot.assign(attr, value);
        return setRoot;
    }
    
    @Override
    public String toString() {
        this.paramList.clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.UPDATE_AS_SET.apply(relRoot.getName(), relRoot.getAlias()));
        
        setRoot.toString(sb);
        
        
        if (whereRoot != null) {
            sb.append(Token.WHERE);
            whereRoot.toString(sb);
        }
        
        if (orderByRoot != null) {
            orderByRoot.toString(sb);
        }
        
        if (limitRoot != null) {
            limitRoot.toString(sb);
        }
        
        return sb.toString();
    }
}

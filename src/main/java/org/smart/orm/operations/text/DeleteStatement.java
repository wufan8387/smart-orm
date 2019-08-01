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

public class DeleteStatement extends AbstractStatement {
    
    
    private ConditionNode<DeleteStatement> whereRoot;
    
    private ConditionNode<DeleteStatement> whereLast;
    
    private LimitNode<DeleteStatement> limitRoot;
    
    private OrderByNode<DeleteStatement> orderByRoot;
    
    private final RelationNode<DeleteStatement> relRoot;
    
    public DeleteStatement(String rel) {
        relRoot = new RelationNode<>(this, rel);
    }

    public DeleteStatement(String rel, String alias) {
        relRoot = new RelationNode<>(this, rel).setAlias(alias);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T attach(T node) {
        
        switch (node.getType()) {
            case NodeType.LIMIT:
                LimitNode<DeleteStatement> limitNode = (LimitNode<DeleteStatement>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<DeleteStatement> orderByNode = (OrderByNode<DeleteStatement>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.CONDITION:
                ConditionNode<DeleteStatement> whereNode = (ConditionNode<DeleteStatement>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
        }
        
        return node;
    }
    
    public ConditionNode<DeleteStatement> where(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast);
        } else {
            return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    public ConditionNode<DeleteStatement> where(String rel, String attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, rel, attr, op, this.whereLast, params);
        } else {
            return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    
    public ConditionNode<DeleteStatement> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<DeleteStatement> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<DeleteStatement> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(this, leftRel, leftAttr, op, rightRel, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public ConditionNode<DeleteStatement> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, rel, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public LimitNode<DeleteStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public DeleteStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public OrderByNode<DeleteStatement> orderBy(String rel, String attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public OrderByNode<DeleteStatement> orderByDesc(String rel, String attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    @Override
    public String toString() {
        this.paramList.clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.DEL_FROM_AS.apply(relRoot.getName(), relRoot.getAlias()));
        
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

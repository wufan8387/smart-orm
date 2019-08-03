package org.smart.orm.operations.type;

import org.smart.orm.data.StatementType;
import org.smart.orm.functions.Func;
import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.LimitNode;
import org.smart.orm.functions.PropertyGetter;

public class DeleteObject extends AbstractStatement {
    
    private ConditionNode<DeleteObject, ?, ?> whereRoot;
    
    private ConditionNode<DeleteObject, ?, ?> whereLast;
    
    private LimitNode<DeleteObject> limitRoot;
    
    private OrderByNode<DeleteObject> orderByRoot;
    
    private RelationNode<DeleteObject, ?> relRoot;
    
    
    public <K extends Model<K>> DeleteObject from(Class<K> cls) {
        relRoot = new RelationNode<>(this, cls);
        return this;
    }
    
    public <K extends Model<K>> DeleteObject from(Class<K> cls, String alias) {
        relRoot = new RelationNode<>(this, cls).setAlias(alias);
        return this;
    }
    
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends SqlNode<?>> void doAttach(T node) {
        
        switch (node.getType()) {
            case NodeType.LIMIT:
                LimitNode<DeleteObject> limitNode = (LimitNode<DeleteObject>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<DeleteObject> orderByNode = (OrderByNode<DeleteObject>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.CONDITION:
                ConditionNode<DeleteObject, ?, ?> whereNode = (ConditionNode<DeleteObject, ?, ?>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<DeleteObject, L, R> where(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast);
        } else {
            return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    public <T extends Model<T>> ConditionNode<DeleteObject, T, ?> where(PropertyGetter<T> attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, attr, op, this.whereLast, params);
        } else {
            return new ConditionNode<>(this, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<DeleteObject, L, R> and(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public <T extends Model<T>> ConditionNode<DeleteObject, T, ?> and(PropertyGetter<T> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<DeleteObject, L, R> or(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public <T extends Model<T>> ConditionNode<DeleteObject, T, ?> or(PropertyGetter<T> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public LimitNode<DeleteObject> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public DeleteObject limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public <T extends Model<T>> OrderByNode<DeleteObject> orderBy(Class<T> rel, PropertyGetter<T> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public <T extends Model<T>> OrderByNode<DeleteObject> orderByDesc(Class<T> rel, PropertyGetter<T> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    @Override
    public String toString() {
        this.getParams().clear();
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

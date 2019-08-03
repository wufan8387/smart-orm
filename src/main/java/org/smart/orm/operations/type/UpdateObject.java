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

public class UpdateObject extends AbstractStatement {
    
    
    private ConditionNode<UpdateObject, ?, ?> whereRoot;
    
    private ConditionNode<UpdateObject, ?, ?> whereLast;
    
    private LimitNode<UpdateObject> limitRoot;
    
    private OrderByNode<UpdateObject> orderByRoot;
    
    private RelationNode<UpdateObject, ?> relRoot;
    
    private SetNode<UpdateObject> setRoot;
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    public <K extends Model<K>> UpdateObject update(Class<K> rel) {
        relRoot = new RelationNode<>(this, rel);
        return this;
    }
    
    public <K extends Model<K>> UpdateObject update(Class<K> rel, String alias) {
        relRoot = new RelationNode<>(this, rel).setAlias(alias);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected  <T extends SqlNode<?>> void doAttach(T node) {
        
        switch (node.getType()) {
            case NodeType.CONDITION:
                ConditionNode<UpdateObject, ?, ?> whereNode = (ConditionNode<UpdateObject, ?, ?>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.LIMIT:
                LimitNode<UpdateObject> limitNode = (LimitNode<UpdateObject>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<UpdateObject> orderByNode = (OrderByNode<UpdateObject>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<UpdateObject, L, R> where(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast);
        } else {
            return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    public <K extends Model<K>> ConditionNode<UpdateObject, K, ?> where(PropertyGetter<K> attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, attr, op, this.whereLast, params);
        } else {
            return new ConditionNode<>(this, attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND);
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<UpdateObject, L, R> and(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public <K extends Model<K>> ConditionNode<UpdateObject, K, ?> and(PropertyGetter<K> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<UpdateObject, L, R> or(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(this, leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public <K extends Model<K>> ConditionNode<UpdateObject, K, ?> or(PropertyGetter<K> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public LimitNode<UpdateObject> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public UpdateObject limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public <K extends Model<K>> OrderByNode<UpdateObject> orderBy(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public <K extends Model<K>> OrderByNode<UpdateObject> orderByDesc(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    public <K extends Model<K>> SetNode<UpdateObject> set(PropertyGetter<K> attr, Object value) {
        if (setRoot == null)
            setRoot = new SetNode<>(this);
        setRoot.assign(attr, value);
        return setRoot;
    }
    
    @Override
    public String toString() {
        this.getParams().clear();
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

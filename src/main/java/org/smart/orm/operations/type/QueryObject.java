package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.functions.Func;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.LimitNode;
import org.smart.orm.reflect.PropertyInfo;

import java.util.List;
import java.util.stream.Collectors;

public class QueryObject extends AbstractStatement {
    
    
    private RelationNode<QueryObject, ?> relRoot;
    
    private RelationNode<QueryObject, ?> relLast;
    
    private ConditionNode<QueryObject, ?, ?> whereRoot;
    
    private ConditionNode<QueryObject, ?, ?> whereLast;
    
    private OrderByNode<QueryObject> orderByRoot;
    
    private GroupByNode<QueryObject> groupByRoot;
    
    
    private LimitNode<QueryObject> limitRoot;
    
    @Override
    public StatementType getType() {
        return StatementType.DQL;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends SqlNode<?>> void doAttach(T node) {
        switch (node.getType()) {
            case NodeType.RELATION:
                RelationNode<QueryObject, ?> relNode = (RelationNode<QueryObject, ?>) node;
                relRoot = relRoot == null ? relNode : relRoot;
                relLast = relNode;
                break;
            case NodeType.CONDITION:
                ConditionNode<QueryObject, ?, ?> whereNode = (ConditionNode<QueryObject, ?, ?>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.GROUP_BY:
                GroupByNode<QueryObject> groupByNode = (GroupByNode<QueryObject>) node;
                groupByRoot = groupByRoot == null ? groupByNode : groupByRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<QueryObject> orderByNode = (OrderByNode<QueryObject>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.LIMIT:
                LimitNode<QueryObject> limitNode = (LimitNode<QueryObject>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
        }
    }
    
    
    public <K extends Model<K>> RelationNode<QueryObject, K> from(Class<K> cls) {
        String rel = Model.getMeta(cls).getTable().getName();
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, cls, relLast)
        );
        
    }
    
    public <K extends Model<K>> RelationNode<QueryObject, K> join(Class<K> cls) {
        
        String rel = Model.getMeta(cls).getTable().getName();
        
        RelationNode<QueryObject, K> node = findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, cls, relLast)
        );
        
        return node.setJoinType(JoinType.INNER);
        
    }
    
    public <T extends Model<T>> AttributeNode<QueryObject, T> select(PropertyGetter<T> attr) {
        return new AttributeNode<>(this, attr);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<QueryObject, L, R> where(PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast);
        }
        return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast).setLogicalType(LogicalType.AND);
        
    }
    
    public <T extends Model<T>> ConditionNode<QueryObject, T, ?> where(PropertyGetter<T> attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, attr, op, whereLast, params);
        } else {
            return new ConditionNode<>(this, attr, op, whereLast, params).setLogicalType(LogicalType.AND);
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<QueryObject, L, R> and(PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr) {
        
        return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public <T extends Model<T>> ConditionNode<QueryObject, T, ?> and(PropertyGetter<T> attr, Func<String>
            op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<QueryObject, L, R> or(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public <T extends Model<T>> ConditionNode<QueryObject, T, ?> or(PropertyGetter<T> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public <T extends Model<T>> OrderByNode<QueryObject> orderBy(Class<T> rel, PropertyGetter<T> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public <T extends Model<T>> OrderByNode<QueryObject> orderByDesc(Class<T> rel, PropertyGetter<T> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    public <T extends Model<T>> GroupByNode<QueryObject> groupBy(Class<T> rel, PropertyGetter<T> attr) {
        if (groupByRoot == null) {
            attach(new GroupByNode<>(this));
        }
        groupByRoot.add(rel, attr);
        return groupByRoot;
    }
    
    
    public LimitNode<QueryObject> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public QueryObject limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.SELECT);
        
        List<AttributeNode<QueryObject, ?>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<QueryObject, ?>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        
        if (attrSize == 0) {
            List<RelationNode<QueryObject, ?>> relList = getNodes()
                    .stream().filter(t -> t.getType() == NodeType.RELATION)
                    .map(t -> (RelationNode<QueryObject, ?>) t)
                    .collect(Collectors.toList());
            for (RelationNode<QueryObject, ?> rel : relList) {
                List<PropertyInfo> propList = rel.getEntityInfo().getPropList();
                for (PropertyInfo prop : propList) {
                    AttributeNode<QueryObject, ?> attrNode = new AttributeNode<>(this, rel, prop);
                    attrList.add(attrNode);
                    attach(attrNode);
                }
            }
        }
        
        attrSize = attrList.size();
        
        for (int i = 0; i < attrSize; i++) {
            SqlNode<?> node = attrList.get(i);
            node.toString(sb);
            if (i < attrSize - 1)
                sb.append(",");
        }
        
        
        sb.append(Token.FROM);
        
        relRoot.toString(sb);
        
        
        if (whereRoot != null) {
            sb.append(Token.WHERE);
            whereRoot.toString(sb);
        }
        
        if (groupByRoot != null) {
            groupByRoot.toString(sb);
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

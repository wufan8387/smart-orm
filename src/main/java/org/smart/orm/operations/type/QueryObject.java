package org.smart.orm.operations.type;

import org.smart.orm.Func;
import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.LimitNode;
import org.smart.orm.reflect.PropertyGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class QueryObject implements Statement {
    
    private final List<Object> paramList = new ArrayList<>();
    
    private final List<SqlNode<?>> nodeList = new ArrayList<>();
    
    private RelationNode<QueryObject, ?> relRoot;
    
    private RelationNode<QueryObject, ?> relLast;
    
    private ConditionNode<QueryObject, ?, ?> whereRoot;
    
    private ConditionNode<QueryObject, ?, ?> whereLast;
    
    private OrderByNode<QueryObject> orderByRoot;
    
    private GroupByNode<QueryObject> groupByRoot;
    
    
    private LimitNode<QueryObject> limitRoot;
    
    @Override
    public UUID getId() {
        return null;
    }
    
    @Override
    public List<Object> getParams() {
        return paramList;
    }
    
    @Override
    public void addParam(Object param) {
        paramList.add(param);
    }
    
    @Override
    public <T extends SqlNode<?>> T attach(T node) {
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
        if (!nodeList.contains(node))
            nodeList.add(node);
        return node;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> List<T> find(int nodeType, Predicate<T> predicate) {
        return nodeList.stream()
                .filter(t -> t.getType() == nodeType)
                .map(t -> (T) t)
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate) {
        return findFirst(nodeType, predicate, () -> null);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other) {
        return nodeList.stream()
                .filter(t -> t.getType() == nodeType)
                .map(t -> (T) t)
                .filter(predicate)
                .findFirst()
                .orElseGet(other);
    }
    
    private int sn = 0;
    
    @Override
    public String alias(String term) {
        sn++;
        return term + "_" + sn;
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
        this.paramList.clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.SELECT);
        
        List<AttributeNode<QueryObject, ?>> attrList = nodeList
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<QueryObject, ?>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        if (attrSize > 0) {
            for (int i = 0; i < attrSize; i++) {
                SqlNode<?> node = attrList.get(i);
                node.toString(sb);
                if (i < attrSize - 1)
                    sb.append(",");
            }
        } else {
            sb.append(" * ");
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

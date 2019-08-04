package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ObjectHandler;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.Func;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.LimitNode;
import org.smart.orm.reflect.PropertyInfo;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class QueryObject<T extends Model<T>> extends AbstractStatement {
    
    
    private final RelationNode<QueryObject<T>, T> relRoot;
    
    private RelationNode<QueryObject<T>, ?> relLast;
    
    private ConditionNode<QueryObject<T>, ?, ?> whereRoot;
    
    private ConditionNode<QueryObject<T>, ?, ?> whereLast;
    
    private OrderByNode<QueryObject<T>> orderByRoot;
    
    private GroupByNode<QueryObject<T>> groupByRoot;
    
    
    private LimitNode<QueryObject<T>> limitRoot;
    
    public QueryObject(Class<T> cls) {
        relRoot = new RelationNode<>(cls, relLast).attach(this);
    }
    
    
    @Override
    public StatementType getType() {
        return StatementType.DQL;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <K extends SqlNode<?, ?>> void doAttach(K node) {
        switch (node.getType()) {
            case NodeType.RELATION:
                relLast = (RelationNode<QueryObject<T>, ?>) node;
                break;
            case NodeType.CONDITION:
                ConditionNode<QueryObject<T>, ?, ?> whereNode = (ConditionNode<QueryObject<T>, ?, ?>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.GROUP_BY:
                GroupByNode<QueryObject<T>> groupByNode = (GroupByNode<QueryObject<T>>) node;
                groupByRoot = groupByRoot == null ? groupByNode : groupByRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<QueryObject<T>> orderByNode = (OrderByNode<QueryObject<T>>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.LIMIT:
                LimitNode<QueryObject<T>> limitNode = (LimitNode<QueryObject<T>>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
        }
    }
    
    
    public <K extends Model<K>> RelationNode<QueryObject<T>, K> join(Class<K> cls) {
        
        String rel = Model.getMeta(cls).getTable().getName();
        
        RelationNode<QueryObject<T>, K> node = findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(cls, relLast).attach(this)
        );
        
        return node.setJoinType(JoinType.INNER);
        
    }
    
    public <K extends Model<K>> AttributeNode<QueryObject<T>, K> select(PropertyGetter<K> attr) {
        return new AttributeNode<QueryObject<T>, K>(attr).attach(this);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<QueryObject<T>, L, R> where(PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(leftAttr, op, rightAttr, whereLast).attach(this);
        }
        return new ConditionNode<>(leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
        
    }
    
    public <K extends Model<K>> ConditionNode<QueryObject<T>, K, ?> where(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(attr, op, whereLast, params).attach(this);
        } else {
            return new ConditionNode<>(attr, op, whereLast, params)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<QueryObject<T>, L, R> and(PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr) {
        
        return new ConditionNode<>(leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public <K extends Model<K>> ConditionNode<QueryObject<T>, K, ?> and(PropertyGetter<K> attr, Func<String>
            op, Object... params) {
        return new ConditionNode<>(attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<QueryObject<T>, L, R> or(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public <K extends Model<K>> ConditionNode<QueryObject<T>, K, ?> or(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    
    public <K extends Model<K>> OrderByNode<QueryObject<T>> orderBy(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            new OrderByNode<QueryObject<T>>().attach(this);
        }
        
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public <K extends Model<K>> OrderByNode<QueryObject<T>> orderByDesc(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            new OrderByNode<QueryObject<T>>().attach(this);
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    public <K extends Model<K>> GroupByNode<QueryObject<T>> groupBy(Class<K> rel, PropertyGetter<K> attr) {
        if (groupByRoot == null) {
            new GroupByNode<QueryObject<T>>().attach(this);
        }
        groupByRoot.add(rel, attr);
        return groupByRoot;
    }
    
    
    public LimitNode<QueryObject<T>> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<QueryObject<T>>().attach(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public QueryObject<T> limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<QueryObject<T>>().attach(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ResultData<T> execute(Connection connection, Executor executor) {
        
        String sql = toString();
        System.out.println(sql);
        
        ObjectHandler<T> handler = new ObjectHandler<>((Class<T>) relRoot.getEntityInfo().getEntityClass());
        
        List<AttributeNode<?, ?>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<?, ?>) t)
                .collect(Collectors.toList());
        
        attrList.forEach(t -> handler.add(t.getAlias(), t.getProp()));
        
        executor.executeQuery(connection, sql, handler, getParams().toArray());
        
        return new ResultData<>(handler.getAll());
        
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.SELECT);
        
        List<AttributeNode<QueryObject<T>, ?>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<QueryObject<T>, ?>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        
        if (attrSize == 0) {
            List<RelationNode<QueryObject<T>, ?>> relList = getNodes()
                    .stream().filter(t -> t.getType() == NodeType.RELATION)
                    .map(t -> (RelationNode<QueryObject<T>, ?>) t)
                    .collect(Collectors.toList());
            for (RelationNode<QueryObject<T>, ?> rel : relList) {
                List<PropertyInfo> propList = rel.getEntityInfo().getPropList();
                for (PropertyInfo prop : propList) {
                    AttributeNode<QueryObject<T>, ?> attrNode = new AttributeNode<>(rel, prop);
                    attrList.add(attrNode);
                    attrNode.attach(this);
                }
            }
        }
        
        attrSize = attrList.size();
        
        for (int i = 0; i < attrSize; i++) {
            SqlNode<?, ?> node = attrList.get(i);
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

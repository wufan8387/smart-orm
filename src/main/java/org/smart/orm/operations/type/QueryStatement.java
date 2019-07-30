package org.smart.orm.operations.type;

import org.smart.orm.Func;
import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.text.*;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class QueryStatement implements Statement {
    
    private RelationNode<QueryStatement, ?> relRoot;
    
    private RelationNode<QueryStatement, ?> relLast;
    
    private ConditionNode<QueryStatement, ?, ?> whereRoot;
    
    private ConditionNode<QueryStatement, ?, ?> whereLast;
    
    private OrderByNode<QueryStatement> orderByRoot;
    
    private GroupByNode<QueryStatement> groupByRoot;
    
    
    private LimitNode<QueryStatement> limitRoot;
    
    @Override
    public UUID getId() {
        return null;
    }
    
    @Override
    public List<Object> getParams() {
        return null;
    }
    
    @Override
    public void addParam(Object param) {
    
    }
    
    @Override
    public <T extends SqlNode<?>> T attach(T node) {
        return null;
    }
    
    @Override
    public <T extends SqlNode<?>> List<T> find(int nodeType, Predicate<T> predicate) {
        return null;
    }
    
    @Override
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate) {
        return null;
    }
    
    @Override
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other) {
        return null;
    }
    
    
    public <K extends Model<K>> RelationNode<QueryStatement, K> from(Class<K> cls) {
        String rel = Model.getMeta(cls).getTable().getName();
        return findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, relLast)
        );
        
    }
    
    
    public <K extends Model<K>> RelationNode<QueryStatement, K> join(Class<K> cls) {
        
        String rel = Model.getMeta(cls).getTable().getName();
        
        RelationNode<QueryStatement, K> node = findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(this, relLast)
        );
        
        return node.setJoinType(JoinType.INNER);
        
    }
    
    
    public <T extends Model<T>> AttributeNode<QueryStatement, T> select(PropertyGetter<T> attr) {
        return new AttributeNode<>(this, attr);
    }
    
    public <T extends Model<T>, K extends Model<K>> ConditionNode<QueryStatement, T, K> where(PropertyGetter<T> leftAttr, Func<String> op, PropertyGetter<K> rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast);
        }
        return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast).setLogicalType(LogicalType.AND);
        
    }
    
    public <T extends Model<T>> ConditionNode<QueryStatement, T, ?> where(PropertyGetter<T> attr, Func<String> op, Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(this, attr, op, whereLast, params);
        } else {
            return new ConditionNode<>(this, attr, op, whereLast, params).setLogicalType(LogicalType.AND);
        }
    }
    
    
    public <T extends Model<T>, K extends Model<K>> ConditionNode<QueryStatement, T, K> and(PropertyGetter<T> leftAttr, Func<String> op, PropertyGetter<K> rightAttr) {
        
        return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.AND);
    }
    
    public <T extends Model<T>> ConditionNode<QueryStatement, T, ?> and(PropertyGetter<T> attr, Func<String>
            op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND);
    }
    
    public <T extends Model<T>, K extends Model<K>> ConditionNode<QueryStatement, T, K> or(PropertyGetter<T> leftAttr
            , Func<String> op
            , PropertyGetter<K> rightAttr) {
        return new ConditionNode<>(this, leftAttr, op, rightAttr, whereLast)
                .setLogicalType(LogicalType.OR);
    }
    
    public <T extends Model<T>> ConditionNode<QueryStatement, T, ?> or(PropertyGetter<T> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(this, attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public <T extends Model<T>> OrderByNode<QueryStatement> orderBy(Class<T> rel, PropertyGetter<T> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        EntityInfo entityInfo = Model.getMeta(rel);
        assert entityInfo != null;
        TableInfo leftTableInfo = entityInfo.getTable();
        String textRel = leftTableInfo.getName();
        
        String textAttr = LambdaParser.getGetter(attr).getName();
        
        orderByRoot.asc(textRel, textAttr);
        return orderByRoot;
    }
    
    public <T extends Model<T>> OrderByNode<QueryStatement> orderByDesc(Class<T> rel, PropertyGetter<T> attr) {
        if (orderByRoot == null) {
            attach(new OrderByNode<>(this));
        }
        EntityInfo entityInfo = Model.getMeta(rel);
        assert entityInfo != null;
        TableInfo leftTableInfo = entityInfo.getTable();
        String textRel = leftTableInfo.getName();
        
        String textAttr = LambdaParser.getGetter(attr).getName();
        orderByRoot.desc(textRel, textAttr);
        return orderByRoot;
    }
    
    public <T extends Model<T>> GroupByNode<QueryStatement> groupBy(Class<T> rel, PropertyGetter<T> attr) {
        if (groupByRoot == null) {
            attach(new GroupByNode<>(this));
        }
        EntityInfo entityInfo = Model.getMeta(rel);
        assert entityInfo != null;
        TableInfo leftTableInfo = entityInfo.getTable();
        String textRel = leftTableInfo.getName();
        
        String textAttr = LambdaParser.getGetter(attr).getName();
        groupByRoot.add(textRel, textAttr);
        return groupByRoot;
    }
    
    
    public LimitNode<QueryStatement> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public QueryStatement limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<>(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    
}

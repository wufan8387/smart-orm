package org.smart.orm.operations.type;

import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.Func;
import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.LimitNode;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;

import java.sql.Connection;
import java.util.List;

public class DeleteObject<T extends Model<T>> extends AbstractStatement {
    
    private ConditionNode<DeleteObject<T>, ?, ?> whereRoot;
    
    private ConditionNode<DeleteObject<T>, ?, ?> whereLast;
    
    private LimitNode<DeleteObject<T>> limitRoot;
    
    private OrderByNode<DeleteObject<T>> orderByRoot;
    
    private RelationNode<DeleteObject<T>, T> relRoot;
    
    
    public DeleteObject(Class<T> cls) {
        relRoot = new RelationNode<DeleteObject<T>, T>(cls).attach(this);
    }
    
    public DeleteObject(Class<T> cls, String alias) {
        relRoot = new RelationNode<DeleteObject<T>, T>(cls).setAlias(alias).attach(this);
    }
    
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <K extends SqlNode<?, ?>> void doAttach(K node) {
        
        switch (node.getType()) {
            case NodeType.LIMIT:
                LimitNode<DeleteObject<T>> limitNode = (LimitNode<DeleteObject<T>>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<DeleteObject<T>> orderByNode = (OrderByNode<DeleteObject<T>>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
            case NodeType.CONDITION:
                ConditionNode<DeleteObject<T>, ?, ?> whereNode = (ConditionNode<DeleteObject<T>, ?, ?>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
        }
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<DeleteObject<T>, L, R> where(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(leftAttr, op, rightAttr, this.whereLast).attach(this);
        } else {
            return new ConditionNode<>(leftAttr, op, rightAttr, this.whereLast)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    public <K extends Model<K>> ConditionNode<DeleteObject<T>, K, ?> where(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        
        if (whereRoot == null) {
            return new ConditionNode<>(attr, op, this.whereLast, params).attach(this);
        } else {
            return new ConditionNode<>(attr, op, this.whereLast, params)
                    .setLogicalType(LogicalType.AND)
                    .attach(this);
        }
    }
    
    public ConditionNode<DeleteObject<T>, T, ?> where(PropertyInfo attr
            , Func<String> op
            , Object... params) {
        
        ConditionNode<DeleteObject<T>, T, ?> node = new ConditionNode<>(relRoot, attr, op, this.whereLast, params);
        if (whereRoot != null) {
            node.setLogicalType(LogicalType.AND);
        }
        node.attach(this);
        return node;
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<DeleteObject<T>, L, R> and(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public <K extends Model<K>> ConditionNode<DeleteObject<T>, K, ?> and(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<DeleteObject<T>, L, R> or(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public <K extends Model<K>> ConditionNode<DeleteObject<T>, K, ?> or(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    
    public LimitNode<DeleteObject<T>> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<DeleteObject<T>>().attach(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public DeleteObject<T> limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<DeleteObject<T>>().attach(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public <K extends Model<K>> OrderByNode<DeleteObject<T>> orderBy(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            new OrderByNode<DeleteObject<T>>().attach(this);
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public <K extends Model<K>> OrderByNode<DeleteObject<T>> orderByDesc(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            new OrderByNode<DeleteObject<T>>().attach(this);
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    @Override
    public ResultData execute(Connection connection, Executor executor) {
        String sql = toString();
        List<Object> params = getParams();
        System.out.println(sql);
        int cnt = executor.update(connection, sql, params.toArray());
        return new ResultData<>(cnt);
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

package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.Func;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.LimitNode;
import org.smart.orm.reflect.PropertyInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateObject<T extends Model<T>> extends AbstractStatement {
    
    
    private ConditionNode<UpdateObject<T>, ?, ?> whereRoot;
    
    private ConditionNode<UpdateObject<T>, ?, ?> whereLast;
    
    private LimitNode<UpdateObject<T>> limitRoot;
    
    private OrderByNode<UpdateObject<T>> orderByRoot;
    
    private RelationNode<UpdateObject<T>, T> relRoot;
    
    
    public UpdateObject(Class<T> rel) {
        relRoot = new RelationNode<UpdateObject<T>, T>(rel).attach(this);
    }
    
    public UpdateObject(Class<T> rel, String alias) {
        this(rel);
        relRoot.setAlias(alias);
    }
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected <K extends SqlNode<?, ?>> void doAttach(K node) {
        
        switch (node.getType()) {
            case NodeType.CONDITION:
                ConditionNode<UpdateObject<T>, ?, ?> whereNode = (ConditionNode<UpdateObject<T>, ?, ?>) node;
                whereRoot = whereRoot == null ? whereNode : whereRoot;
                whereLast = whereNode;
                break;
            case NodeType.LIMIT:
                LimitNode<UpdateObject<T>> limitNode = (LimitNode<UpdateObject<T>>) node;
                limitRoot = limitRoot == null ? limitNode : limitRoot;
                break;
            case NodeType.ORDER_BY:
                OrderByNode<UpdateObject<T>> orderByNode = (OrderByNode<UpdateObject<T>>) node;
                orderByRoot = orderByRoot == null ? orderByNode : orderByRoot;
                break;
        }
    }
    
    
    public UpdateObject<T> assign(PropertyGetter<T> attr, Object value) {
        new AssignNode<>(relRoot, attr, value).attach(this);
        return this;
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<UpdateObject<T>, L, R> where(PropertyGetter<L> leftAttr
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
    
    public <K extends Model<K>> ConditionNode<UpdateObject<T>, K, ?> where(PropertyGetter<K> attr
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
    
    public ConditionNode<UpdateObject<T>, T, ?> where(PropertyInfo attr
            , Func<String> op
            , Object... params) {
        
        ConditionNode<UpdateObject<T>, T, ?> node = new ConditionNode<>(relRoot
                , attr
                , op
                , this.whereLast
                , params);
        
        if (whereRoot != null) {
            node.setLogicalType(LogicalType.AND);
        }
        node.attach(this);
        return node;
    }
    
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<UpdateObject<T>, L, R> and(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public <K extends Model<K>> ConditionNode<UpdateObject<T>, K, ?> and(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.AND)
                .attach(this);
    }
    
    public <L extends Model<L>, R extends Model<R>> ConditionNode<UpdateObject<T>, L, R> or(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, this.whereLast)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    public <K extends Model<K>> ConditionNode<UpdateObject<T>, K, ?> or(PropertyGetter<K> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(attr, op, this.whereLast, params)
                .setLogicalType(LogicalType.OR)
                .attach(this);
    }
    
    
    public LimitNode<UpdateObject<T>> limit(int start) {
        if (limitRoot == null)
            limitRoot = new LimitNode<UpdateObject<T>>().attach(this);
        limitRoot.setStart(start);
        return limitRoot;
    }
    
    public UpdateObject<T> limit(int start, int end) {
        if (limitRoot == null)
            limitRoot = new LimitNode<UpdateObject<T>>().attach(this);
        limitRoot.setStart(start).setEnd(end);
        return this;
    }
    
    public <K extends Model<K>> OrderByNode<UpdateObject<T>> orderBy(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            new OrderByNode<>().attach(this);
        }
        orderByRoot.asc(rel, attr);
        return orderByRoot;
    }
    
    public <K extends Model<K>> OrderByNode<UpdateObject<T>> orderByDesc(Class<K> rel, PropertyGetter<K> attr) {
        if (orderByRoot == null) {
            new OrderByNode<>().attach(this);
        }
        orderByRoot.desc(rel, attr);
        return orderByRoot;
    }
    
    
    @Override
    public void execute(Executor executor) {
        String sql = toString();
        System.out.println(sql);
        List<Object> params = getParams();
        int cnt = executor.update(sql, params.toArray());
        setResult(new ResultData<>(cnt));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.UPDATE_AS_SET.apply(relRoot.getName(), relRoot.getAlias()));
        
        List<AssignNode<UpdateObject<T>, ?>> assignList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ASSIGN)
                .map(t -> (AssignNode<UpdateObject<T>, ?>) t)
                .collect(Collectors.toList());
        
        int assignLen = assignList.size();
        for (int i = 0; i < assignLen; i++) {
            AssignNode<UpdateObject<T>, ?> assignNode = assignList.get(i);
            assignNode.toString(sb);
            if (i < assignLen - 1)
                sb.append(" , ");
        }
        
        
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

package org.smart.orm.operations.type;

import org.smart.orm.Func;
import org.smart.orm.Model;
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

public class DeleteObject implements Statement {

    private final List<Object> paramList = new ArrayList<>();

    private ConditionNode<DeleteObject, ?, ?> whereRoot;

    private ConditionNode<DeleteObject, ?, ?> whereLast;

    private LimitNode<DeleteObject> limitRoot;

    private OrderByNode<DeleteObject> orderByRoot;

    private RelationNode<DeleteObject, ?> relRoot;

    private int sn = 0;

    @Override
    public String alias(String term) {
        sn++;
        return term + "_" + sn;
    }

    public <K extends Model<K>> DeleteObject from(Class<K> cls) {
        relRoot = new RelationNode<>(this, cls);
        return this;
    }

    public <K extends Model<K>> DeleteObject from(Class<K> cls, String alias) {
        relRoot = new RelationNode<>(this, cls).setAlias(alias);
        return this;
    }

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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T attach(T node) {

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

        return node;
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

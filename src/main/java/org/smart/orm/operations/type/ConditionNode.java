package org.smart.orm.operations.type;

import org.smart.orm.Func;
import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.execution.KeyMapHandler;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.PropertyGetter;

import javax.jws.WebParam;

public class ConditionNode<T extends Statement, L extends Model<L>, R extends Model<R>> implements SqlNode<T> {
    
    private RelationNode<T, L> leftRel;
    
    private RelationNode<T, R> rightRel;
    
    private PropertyGetter<L> leftAttr;
    
    private PropertyGetter<R> rightAttr;
    
    private Object[] params;
    
    private Func<String> op;
    
    
    private ConditionNode<T, ?, ?> child;
    
    private T statement;
    
    private LogicalType logicalType = null;
    
    
    public ConditionNode(T statement, PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr) {
        this.statement = statement;
        Class<?> cls = this.getClass();
        this.leftRel = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(cls, 1).getTable().getName()));
        this.rightRel = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(cls, 2).getTable().getName()));
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr,
                         ConditionNode<T, ?, ?> parent) {
        this(statement, leftAttr, op, rightAttr);
        parent.child = this;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, RelationNode<T, L> leftRel, PropertyGetter<L> leftAttr, Func<String> op, RelationNode<T, R> rightRel, PropertyGetter<R> rightAttr) {
        this.statement = statement;
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, RelationNode<T, L> leftRel, PropertyGetter<L> leftAttr, Func<String> op, RelationNode<T, R> rightRel, PropertyGetter<R> rightAttr, ConditionNode<T, ?, ?> parent) {
        this(statement, leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode(T statement, PropertyGetter<L> attr, Func<String> op, Object... params) {
        this.statement = statement;
        this.leftRel = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(this.getClass(), 1).getTable().getName()));
        this.leftAttr = attr;
        this.params = params;
        this.op = op;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, PropertyGetter<L> attr, Func<String> op, ConditionNode<T, ?, ?> parent, Object... params) {
        this(statement, attr, op, params);
        if (parent != null)
            parent.child = this;
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> and(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        
        return new ConditionNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND);
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> and(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new ConditionNode<>(statement, leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.AND);
    }
    
    public <NL extends Model<NL>> ConditionNode<T, NL, ?> and(PropertyGetter<NL> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(statement, attr, op, this, params)
                .setLogicalType(LogicalType.AND);
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> or(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new ConditionNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR);
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> or(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new ConditionNode<>(statement, leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.OR);
    }
    
    public <NL extends Model<NL>> ConditionNode<T, NL, ?> or(PropertyGetter<NL> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(statement, attr, op, this, params)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public LogicalType getLogicalType() {
        return logicalType;
    }
    
    
    public ConditionNode<T, L, R> setLogicalType(LogicalType logicalType) {
        this.logicalType = logicalType;
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.CONDITION;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Op.LOGICAL.apply(logicalType));
        
        if (rightRel != null) {
            sb.append(op.apply(leftRel.getAlias(), leftAttr, rightRel.getAlias(), rightAttr));
        } else {
            sb.append(op.apply(leftRel.getAlias(), leftAttr));
        }
        if (child != null)
            child.toString(sb);
        if (params != null && params.length > 0)
            statement.addParam(params);
    }
    
    
}

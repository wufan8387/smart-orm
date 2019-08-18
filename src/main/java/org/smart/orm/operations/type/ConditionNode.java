package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.functions.Func;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyInfo;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ConditionNode<T extends Statement, L extends Model<L>, R extends Model<R>> extends AbstractSqlNode<T, ConditionNode<T, L, R>> {
    
    private RelationNode<T, L> leftRel;
    
    private RelationNode<T, R> rightRel;
    
    private PropertyGetter<L> leftAttr;
    
    private PropertyGetter<R> rightAttr;
    
    private PropertyInfo leftProp;
    
    private PropertyInfo rightProp;
    
    private Func<String> op;
    
    private ConditionNode<T, ?, ?> child;
    
    private LogicalType logicalType = null;
    
    
    private Supplier<Object[]> parameterGetter;
    
    public ConditionNode(PropertyGetter<L> leftAttr, Func<String> op, PropertyGetter<R> rightAttr) {
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public ConditionNode(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr
            , ConditionNode<T, ?, ?> parent) {
        this(leftAttr, op, rightAttr);
        parent.child = this;
    }
    
    public ConditionNode(RelationNode<T, L> leftRel, PropertyGetter<L> leftAttr
            , Func<String> op
            , RelationNode<T, R> rightRel, PropertyGetter<R> rightAttr) {
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        
        this.op = op;
    }
    
    public ConditionNode(RelationNode<T, L> leftRel, PropertyGetter<L> leftAttr
            , Func<String> op
            , RelationNode<T, R> rightRel, PropertyGetter<R> rightAttr
            , ConditionNode<T, ?, ?> parent) {
        this(leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode(PropertyGetter<L> attr, Func<String> op, Object... params) {
        this.leftAttr = attr;
        this.op = op;
        setParams(params);
    }
    
    public ConditionNode(RelationNode<T, L> rel, PropertyInfo attr, Func<String> op, Object... params) {
        
        this.leftRel = rel;
        this.leftProp = attr;
        this.op = op;
        setParams(params);
    }
    
    public ConditionNode(RelationNode<T, L> rel, PropertyInfo attr, Func<String> op, Supplier<Object[]> params) {
        this.leftRel = rel;
        this.leftProp = attr;
        this.op = op;
        setParams(params);
    }
    
    public ConditionNode(RelationNode<T, L> rel
            , PropertyInfo attr
            , Func<String> op
            , ConditionNode<T, ?, ?> parent
            , Object... params) {
        this(rel, attr, op, params);
        if (parent != null)
            parent.child = this;
    }
    
    public ConditionNode(RelationNode<T, L> rel
            , PropertyInfo attr
            , Func<String> op
            , ConditionNode<T, ?, ?> parent
            , Supplier<Object[]> params) {
        this(rel, attr, op, params);
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode(PropertyGetter<L> attr, Func<String> op, ConditionNode<T, ?, ?> parent, Object... params) {
        this(attr, op, params);
        if (parent != null)
            parent.child = this;
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> and(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> and(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public <NL extends Model<NL>> ConditionNode<T, NL, ?> and(PropertyGetter<NL> attr
            , Func<String> op
            , Object... params) {
        return new ConditionNode<>(attr, op, this, params)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> or(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> ConditionNode<T, NL, NR> or(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new ConditionNode<>(leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public <NL extends Model<NL>> ConditionNode<T, NL, ?> or(PropertyGetter<NL> attr, Func<String> op, Object... params) {
        return new ConditionNode<>(attr, op, this, params)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    
    public LogicalType getLogicalType() {
        return logicalType;
    }
    
    
    public ConditionNode<T, L, R> setLogicalType(LogicalType logicalType) {
        this.logicalType = logicalType;
        return this;
    }
    
    @Override
    public ConditionNode<T, L, R> attach(T statement) {
        
        if (leftProp == null) {
            Field field = LambdaParser.getGetter(leftAttr);
            Class<?> cls = field.getDeclaringClass();
            
            if (leftRel == null) {
                leftRel = statement.findFirst(NodeType.RELATION
                        , t -> t.getName().equals(Model.getMetaManager().findEntityInfo(cls).getTableName()));
            }
            if (leftProp == null) {
                leftProp = leftRel.getEntityInfo().getProp(field.getName());
            }
        }
        
        
        if (rightAttr != null && rightProp == null) {
            
            Field field = LambdaParser.getGetter(rightAttr);
            Class<?> cls = field.getDeclaringClass();
            
            if (rightRel == null) {
                rightRel = statement.findFirst(NodeType.RELATION
                        , t -> t.getName().equals(Model.getMetaManager().findEntityInfo(cls).getTableName()));
            }
            if (rightProp == null) {
                rightProp = rightRel.getEntityInfo().getProp(field.getName());
            }
        }
        
        
        return super.attach(statement);
    }
    
    @Override
    public int getType() {
        return NodeType.CONDITION;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Op.LOGICAL.apply(logicalType));
        Object[] params = getParams().get();
        if (rightRel != null) {
            sb.append(op.apply(leftRel.getAlias(), leftProp.getColumnName(), rightRel.getAlias(), rightProp.getColumnName(), params));
        } else {
            sb.append(op.apply(leftRel.getAlias(), leftProp.getColumnName(), params));
        }
        T statement = statement();
        if (params != null && params.length > 0) {
            for (Object param : params) {
                statement.getParams().add(param);
            }
        }
        if (child != null)
            child.toString(sb);
        
    }
    
    
}

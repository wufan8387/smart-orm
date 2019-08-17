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

public class JoinNode<T extends Statement, L extends Model<L>, R extends Model<R>> extends AbstractSqlNode<T, JoinNode<T, L, R>> {
    
    private RelationNode<T, L> leftRel;
    
    private RelationNode<T, R> rightRel;
    
    private PropertyGetter<L> leftAttr;
    
    private PropertyGetter<R> rightAttr;
    
    private PropertyInfo leftProp;
    
    private PropertyInfo rightProp;
    
    private Func<String> op;
    
    private JoinNode<T, ?, ?> child;
    
    private LogicalType logicalType = null;
    
    
    public JoinNode(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public JoinNode(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr
            , JoinNode<T, ?, ?> parent) {
        this(leftAttr, op, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    public JoinNode(RelationNode<T, L> leftRel
            , PropertyGetter<L> leftAttr
            , Func<String> op
            , RelationNode<T, R> rightRel
            , PropertyGetter<R> rightAttr) {
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public JoinNode(RelationNode<T, L> leftRel
            , PropertyGetter<L> leftAttr
            , Func<String> op
            , RelationNode<T, R> rightRel
            , PropertyGetter<R> rightAttr
            , JoinNode<T, ?, ?> parent) {
        this(leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> and(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> and(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> or(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> or(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public LogicalType getLogicalType() {
        return logicalType;
    }
    
    
    public JoinNode<T, L, R> setLogicalType(LogicalType logicalType) {
        this.logicalType = logicalType;
        return this;
    }
    
    @Override
    public JoinNode<T, L, R> attach(T statement) {
        
        Field leftField = LambdaParser.getGetter(leftAttr);
        Class<?> leftCls = leftField.getDeclaringClass();
        
        if (leftRel == null) {
            leftRel = statement.findFirst(NodeType.RELATION
                    , t -> t.getName().equals(Model.getMetaManager().findEntityInfo(leftCls).getTableName()));
        }
        leftProp = Model
                .getMetaManager()
                .findEntityInfo(leftCls)
                .getProp(leftField.getName());
        
        Field rightField = LambdaParser.getGetter(rightAttr);
        Class<?> rightCls = rightField.getDeclaringClass();
        if (rightRel == null) {
            rightRel = statement.findFirst(NodeType.RELATION
                    , t -> t.getName().equals(Model.getMetaManager().findEntityInfo(rightCls).getTableName()));
        }
        
        rightProp = Model
                .getMetaManager()
                .findEntityInfo(rightCls)
                .getProp(rightField.getName());
        
        
        return super.attach(statement);
    }
    
    @Override
    public int getType() {
        return NodeType.CONDITION_JOIN;
    }
    
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Op.LOGICAL.apply(logicalType));
        
        sb.append(op.apply(leftRel.getAlias()
                , leftProp.getColumnName()
                , rightRel.getAlias()
                , rightProp.getColumnName()));
        if (child != null)
            child.toString(sb);
    }
    
    
}

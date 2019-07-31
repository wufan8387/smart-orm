package org.smart.orm.operations.type;

import org.smart.orm.Func;
import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;

public class JoinNode<T extends Statement, L extends Model<L>, R extends Model<R>> implements SqlNode<T> {
    
    private RelationNode<T, L> leftRel;
    
    private RelationNode<T, R> rightRel;
    
    private PropertyGetter<L> leftAttr;
    
    private PropertyGetter<R> rightAttr;
    
    private Func<String> op;
    
    private JoinNode<T, ?, ?> child;
    
    private T statement;
    
    private LogicalType logicalType = null;
    
    
    public JoinNode(T statement
            , PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        this.statement = statement;
        
        Class<?> leftCls = LambdaParser.getGetter(leftAttr).getDeclaringClass();
        Class<?> rightCls = LambdaParser.getGetter(rightAttr).getDeclaringClass();
    
    
        this.leftRel = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(leftCls).getTable().getName()));
        this.rightRel = statement.findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMeta(rightCls).getTable().getName()));
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
        statement.attach(this);
    }
    
    public JoinNode(T statement
            , PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr
            , JoinNode<T, ?, ?> parent) {
        this(statement, leftAttr, op, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    public JoinNode(T statement
            , RelationNode<T, L> leftRel
            , PropertyGetter<L> leftAttr
            , Func<String> op
            , RelationNode<T, R> rightRel
            , PropertyGetter<R> rightAttr) {
        this.statement = statement;
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
        statement.attach(this);
    }
    
    public JoinNode(T statement
            , RelationNode<T, L> leftRel
            , PropertyGetter<L> leftAttr
            , Func<String> op
            , RelationNode<T, R> rightRel
            , PropertyGetter<R> rightAttr
            , JoinNode<T, ?, ?> parent) {
        this(statement, leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> and(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND);
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> and(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(statement, leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.AND);
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> or(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR);
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> or(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>(statement, leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.OR);
    }
    
    public LogicalType getLogicalType() {
        return logicalType;
    }
    
    
    public JoinNode<T, L, R> setLogicalType(LogicalType logicalType) {
        this.logicalType = logicalType;
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.CONDITION_JOIN;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Op.LOGICAL.apply(logicalType));
        
        String leftTextAttr=LambdaParser.getGetter(leftAttr).getName();
        String rightTextAttr=LambdaParser.getGetter(rightAttr).getName();
    
    
        sb.append(op.apply(leftRel.getAlias(), leftTextAttr, rightRel.getAlias(), rightTextAttr));
        if (child != null)
            child.toString(sb);
    }
    
    
}
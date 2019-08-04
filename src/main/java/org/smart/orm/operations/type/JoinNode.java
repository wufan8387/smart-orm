package org.smart.orm.operations.type;

import org.smart.orm.functions.Func;
import org.smart.orm.Model;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.functions.PropertyGetter;

public class JoinNode<T extends Statement, L extends Model<L>, R extends Model<R>> extends AbstractSqlNode<T, JoinNode<T, L, R>> {
    
    private RelationNode<T, L> leftRel;
    
    private RelationNode<T, R> rightRel;
    
    private PropertyGetter<L> leftAttr;
    
    private PropertyGetter<R> rightAttr;
    
    private Func<String> op;
    
    private JoinNode<T, ?, ?> child;
    
    private LogicalType logicalType = null;
    
    
    public JoinNode(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        
        Class<?> leftCls = LambdaParser.getGetter(leftAttr).getDeclaringClass();
        Class<?> rightCls = LambdaParser.getGetter(rightAttr).getDeclaringClass();
        
        
//        this.leftRel = statement.findFirst(NodeType.RELATION
//                , t -> t.getName().equals(Model.getMeta(leftCls).getTable().getName()));
//        this.rightRel = statement.findFirst(NodeType.RELATION
//                , t -> t.getName().equals(Model.getMeta(rightCls).getTable().getName()));
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public JoinNode(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr
            , JoinNode<T, ?, ?> parent) {
        this( leftAttr, op, rightAttr);
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
        this( leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> and(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> and(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>( leftAttr, op, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> or(RelationNode<T, NL> leftRel
            , PropertyGetter<NL> leftAttr
            , Func<String> op
            , RelationNode<T, NR> rightRel
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    
    public <NL extends Model<NL>, NR extends Model<NR>> JoinNode<T, NL, NR> or(PropertyGetter<NL> leftAttr
            , Func<String> op
            , PropertyGetter<NR> rightAttr) {
        return new JoinNode<>( leftAttr, op, rightAttr, this)
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
    public int getType() {
        return NodeType.CONDITION_JOIN;
    }
    
    
    @Override
    public void toString(StringBuilder sb) {
        
        sb.append(Op.LOGICAL.apply(logicalType));
        
        String leftTextAttr = Model
                .getMeta(leftRel.getEntityInfo().getEntityClass())
                .getProp(leftAttr).getColumnName();
        String rightTextAttr = Model
                .getMeta(rightRel.getEntityInfo().getEntityClass())
                .getProp(rightAttr).getColumnName();
        
        sb.append(op.apply(leftRel.getAlias(), leftTextAttr, rightRel.getAlias(), rightTextAttr));
        if (child != null)
            child.toString(sb);
    }
    
    
}

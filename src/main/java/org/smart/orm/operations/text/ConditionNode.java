package org.smart.orm.operations.text;

import org.smart.orm.functions.Func;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.jdbc.SetParameter;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;


public class ConditionNode<T extends Statement> extends AbstractSqlNode<T, ConditionNode<T>> {
    
    
    private RelationNode<T> leftRel;
    
    private RelationNode<T> rightRel;
    
    private String leftAttr;
    
    private String rightAttr;
    
    private Func<String> op;
    
    private ConditionNode<T> child;
    
    private LogicalType logicalType = null;
    
    
    public ConditionNode(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
//        this.leftRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(leftRel));
//        this.rightRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rightRel));
        
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public ConditionNode(String leftRel, String leftAttr, Func<String> op, String rightRel, String
            rightAttr, ConditionNode<T> parent) {
        this(leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    public ConditionNode(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public ConditionNode(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr, ConditionNode<T> parent) {
        this(leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode(String rel, String attr, Func<String> op, Object... params) {

//        this.leftRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rel));
        this.leftAttr = attr;
        setParams(params);
        this.op = op;
    }
    
    public ConditionNode(String rel, String attr, Func<String> op, ConditionNode<T> parent, Object... params) {
        this(rel, attr, op, params);
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode<T> and(RelationNode<T> leftRel
            , String leftAttr
            , Func<String> op
            , RelationNode<T> rightRel
            , String rightAttr) {
        
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public ConditionNode<T> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public ConditionNode<T> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>( rel, attr, op, this, params)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    
    public ConditionNode<T> or(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        return new ConditionNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public ConditionNode<T> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public ConditionNode<T> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>( rel, attr, op, this, params)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    
    public LogicalType getLogicalType() {
        return logicalType;
    }
    
    
    public ConditionNode<T> setLogicalType(LogicalType logicalType) {
        this.logicalType = logicalType;
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.CONDITION;
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
        Object[] params = getParams();
        if (params != null && params.length > 0)
            statement().getParams().add(params);
    }
}

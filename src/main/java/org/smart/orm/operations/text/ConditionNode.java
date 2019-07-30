package org.smart.orm.operations.text;

import org.smart.orm.Func;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;


public class ConditionNode<T extends Statement> implements SqlNode<T> {
    
    
    private RelationNode<T> leftRel;
    
    private RelationNode<T> rightRel;
    
    private String leftAttr;
    
    private String rightAttr;
    
    private Object[] params;
    
    private Func<String> op;
    
//    private ConditionNode<T> parent;
    
    private ConditionNode<T> child;
    
    private T statement;
    
    private LogicalType logicalType = null;
    
    
    public ConditionNode(T statement, String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        this.statement = statement;
        this.leftRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(leftRel));
        this.rightRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rightRel));
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, String leftRel, String leftAttr, Func<String> op, String rightRel, String
            rightAttr, ConditionNode<T> parent) {
        this(statement, leftRel, leftAttr, op, rightRel, rightAttr);
//        this.parent = parent;
        parent.child = this;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        this.statement = statement;
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr, ConditionNode<T> parent) {
        this(statement, leftRel, leftAttr, op, rightRel, rightAttr);
//        this.parent = parent;
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode(T statement, String rel, String attr, Func<String> op, Object... params) {
        this.statement = statement;
        this.leftRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rel));
        this.leftAttr = attr;
        this.params = params;
        this.op = op;
        statement.attach(this);
    }
    
    public ConditionNode(T statement, String rel, String attr, Func<String> op, ConditionNode<T> parent, Object...
            params) {
        this(statement, rel, attr, op, params);
//        this.parent = parent;
        if (parent != null)
            parent.child = this;
    }
    
    
    public ConditionNode<T> and(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        
        return new ConditionNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<T> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND);
    }
    
    public ConditionNode<T> and(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(statement, rel, attr, op, this, params)
                .setLogicalType(LogicalType.AND);
    }
    
    
    public ConditionNode<T> or(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        return new ConditionNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR);
    }
    
    public ConditionNode<T> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new ConditionNode<>(statement, leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR);
    }
    
    public ConditionNode<T> or(String rel, String attr, Func<String> op, Object... params) {
        return new ConditionNode<>(statement, rel, attr, op, this, params)
                .setLogicalType(LogicalType.OR);
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

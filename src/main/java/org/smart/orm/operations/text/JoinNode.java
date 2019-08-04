package org.smart.orm.operations.text;

import org.smart.orm.functions.Func;
import org.smart.orm.data.LogicalType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

public class JoinNode<T extends Statement> extends AbstractSqlNode<T, JoinNode<T>> {
    
    
    private RelationNode<T> leftRel;
    
    private RelationNode<T> rightRel;
    
    private String leftAttr;
    
    private String rightAttr;
    
    private Func<String> op;
    
    private JoinNode<T> child;
    
    private LogicalType logicalType = null;
    
    
    public JoinNode(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
//        this.leftRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(leftRel));
//        this.rightRel = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rightRel));
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public JoinNode(String leftRel, String leftAttr, Func<String> op, String rightRel, String
            rightAttr, JoinNode<T> parent) {
        this(leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    public JoinNode(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        this.leftRel = leftRel;
        this.rightRel = rightRel;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
        this.op = op;
    }
    
    public JoinNode(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr, JoinNode<T> parent) {
        this(leftRel, leftAttr, op, rightRel, rightAttr);
        if (parent != null)
            parent.child = this;
    }
    
    
    public JoinNode<T> and(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        return new JoinNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public JoinNode<T> and(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new JoinNode<>(leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    public JoinNode<T> and(String leftAttr, Func<String> op, String rightAttr) {
        return new JoinNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.AND)
                .attach(statement());
    }
    
    
    public JoinNode<T> or(RelationNode<T> leftRel, String leftAttr, Func<String> op, RelationNode<T> rightRel, String rightAttr) {
        return new JoinNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public JoinNode<T> or(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        return new JoinNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    
    public JoinNode<T> or(String leftAttr, Func<String> op, String rightAttr) {
        return new JoinNode<>( leftRel, leftAttr, op, rightRel, rightAttr, this)
                .setLogicalType(LogicalType.OR)
                .attach(statement());
    }
    
    public LogicalType getLogicalType() {
        return logicalType;
    }
    
    
    public JoinNode<T> setLogicalType(LogicalType logicalType) {
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
        sb.append(op.apply(leftRel.getAlias(), leftAttr, rightRel.getAlias(), rightAttr));
        if (child != null)
            child.toString(sb);
    }
    
    
}

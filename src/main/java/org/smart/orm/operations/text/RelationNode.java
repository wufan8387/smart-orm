package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.functions.Func;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.*;

public class RelationNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    private String name;
    
    private String alias;
    
    private RelationNode<T> parent;
    
    private RelationNode<T> child;
    
    private JoinType joinType = JoinType.NONE;
    
    private JoinNode<T> joinRoot;
    
    private JoinNode<T> joinLast;
    
    @Override
    public T statement() {
        return statement;
    }
    
    public RelationNode(T statement, String name) {
        this.statement = statement;
        this.name = name;
        statement.attach(this);
    }
    
    public RelationNode(T statement, String name, RelationNode<T> parent) {
        this(statement, name);
        this.parent = parent;
        if (parent != null)
            parent.child = this;
    }
    
    
    @Override
    public int getType() {
        return NodeType.RELATION;
    }
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAlias() {
        if (StringUtils.isNotEmpty(alias))
            return alias;
        return name;
    }
    
    public RelationNode<T> setAlias(String alias) {
        this.alias = alias;
        return this;
    }
    
    public RelationNode<T> join(String rel) {
        return statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(statement, rel, this)
        )
                .setJoinType(JoinType.INNER);
    }
    
    public RelationNode<T> join(String rel, String alias) {
        return statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<>(statement, rel, this)
        )
                .setAlias(alias)
                .setJoinType(JoinType.INNER);
    }
    
    
    public JoinNode<T> on(String leftAttr, Func<String> op, String rightAttr) {
        joinLast = new JoinNode<>(statement, this.parent, leftAttr, op, this, rightAttr, joinLast);
        joinRoot = joinRoot == null ? joinLast : joinRoot;
        return joinLast;
    }
    
    public JoinNode<T> on(String leftRel, String leftAttr, Func<String> op, String rightRel, String rightAttr) {
        RelationNode<T> leftNode = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(leftRel));
        RelationNode<T> rightNode = statement.findFirst(NodeType.RELATION, t -> t.getName().equals(rightRel));
        joinLast = new JoinNode<>(statement, leftNode, leftAttr, op, rightNode, rightAttr, joinLast);
        joinRoot = joinRoot == null ? joinLast : joinRoot;
        return joinLast;
    }
    
    
    public RelationNode<T> select(String attr) {
        return statement.attach(new AttributeNode<>(statement, attr)).from(this);
    }
    
    public RelationNode<T> select(String attr, String alias) {
        return statement.attach(new AttributeNode<>(statement, attr, alias)).from(this);
    }
    
    
    public RelationNode<T> getParent() {
        return parent;
    }
    
    public RelationNode<T> getChild() {
        return child;
    }
    
    public JoinType getJoinType() {
        return joinType;
    }
    
    public RelationNode<T> setJoinType(JoinType joinType) {
        this.joinType = joinType;
        return this;
    }
    
    public void toString(StringBuilder sb) {
        
        if (parent != null) {
            sb.append(Token.JOIN.apply(joinType));
        }
        
        sb.append(Token.REL_AS.apply(name, getAlias()));
        
        if (joinRoot != null) {
            sb.append(Token.ON);
            joinRoot.toString(sb);
        }
        
        if (this.child == null)
            return;
        this.child.toString(sb);
        
    }
    
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (!(obj instanceof RelationNode))
            return false;
        
        RelationNode data = (RelationNode) obj;
        
        return data.name.equals(name);
        
        
    }
}

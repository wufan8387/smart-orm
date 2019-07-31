package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Func;
import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.reflect.PropertyGetter;

import java.nio.channels.ClosedSelectorException;

public class RelationNode<T extends Statement, K extends Model<K>> implements SqlNode<T> {
    
    private T statement;
    
    private String name;
    private String alias;
    
    private RelationNode<T, ?> parent;
    
    private RelationNode<T, ?> child;
    
    private JoinType joinType = JoinType.NONE;
    
    private JoinNode<T, ?, ?> joinRoot;
    
    private JoinNode<T, ?, ?> joinLast;
    
    @Override
    public T statement() {
        return statement;
    }
    
    public RelationNode(T statement, Class<K> cls) {
        this.statement = statement;
        name = Model.getMeta(cls).getTable().getName();
        statement.attach(this);
    }
    
    
    public RelationNode(T statement, Class<K> cls, RelationNode<T, ?> parent) {
        this(statement, cls);
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
        String name = getName();
        alias = statement.alias(name);
        return alias;
    }
    
    public RelationNode<T, K> setAlias(String alias) {
        this.alias = alias;
        return this;
    }
    
    public <U extends Model<U>> RelationNode<T, U> join(Class<U> rel) {
        RelationNode<T, U> node = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(Model.getMeta(rel).getTable().getName()),
                () -> new RelationNode<>(statement, rel, this));
        return node.setJoinType(JoinType.INNER);
    }
    
    @SuppressWarnings("unchecked")
    public <L extends Model<L>, R extends Model<R>> JoinNode<T, L, R> on(PropertyGetter<L> leftAttr
            , Func<String> op
            , PropertyGetter<R> rightAttr) {
        joinLast = new JoinNode<>(statement, leftAttr, op, rightAttr, joinLast);
        joinRoot = joinRoot == null ? joinLast : joinRoot;
        return (JoinNode<T, L, R>) joinLast;
    }
    
    
    public RelationNode<T, K> select(PropertyGetter<K> attr) {
        return statement.attach(new AttributeNode<>(statement, attr)).from(this);
    }
    
    public RelationNode<T, K> select(PropertyGetter<K> attr, String alias) {
        return statement.attach(new AttributeNode<>(statement, attr, alias)).from(this);
    }
    
    @SuppressWarnings("unchecked")
    public <P extends Model<P>> RelationNode<T, P> getParent() {
        return (RelationNode<T, P>) parent;
    }
    
    @SuppressWarnings("unchecked")
    public <C extends Model<C>> RelationNode<T, C> getChild() {
        return (RelationNode<T, C>) child;
    }
    
    public JoinType getJoinType() {
        return joinType;
    }
    
    public RelationNode<T, K> setJoinType(JoinType joinType) {
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
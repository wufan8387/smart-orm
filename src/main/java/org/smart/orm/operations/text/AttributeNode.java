package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;


public class AttributeNode<T extends Statement> implements SqlNode<T> {
    
    private T statement;
    
    private String name;
    private String alias;
    
    private SqlNode<T> internalNode;
    
    private RelationNode<T> rel;
    
    public AttributeNode(T statement, String name) {
        this.statement = statement;
        this.name = name;
    }
    
    public AttributeNode(T statement, String name, String alias) {
        this.statement = statement;
        this.name = name;
        this.alias = alias;
    }
    
    public AttributeNode(T statement, SqlNode<T> internalNode, String alias) {
        this.statement = statement;
        this.internalNode = internalNode;
        this.alias = alias;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAlias() {
        if (StringUtils.isNotEmpty(alias))
            return alias;
        return name;
    }
    
    public AttributeNode<T> setAlias(String alias) {
        this.alias = alias;
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.ATTRIBUTE;
    }
    
    @Override
    public T statement() {
        return statement;
    }
    
    public RelationNode<T> from(String rel) {
        this.rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> statement.attach(new RelationNode<>(statement, rel))
        );
        statement.attach(this);
        return this.rel;
    }
    
    public RelationNode<T> from(String rel, String alias) {
        this.rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> statement.attach(new RelationNode<>(statement, rel))
        )
                .setAlias(alias);
        statement.attach(this);
        return this.rel;
    }
    
    public RelationNode<T> from(RelationNode<T> rel) {
        this.rel = rel;
        statement.attach(this);
        return rel;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        if (internalNode == null) {
            sb.append(Token.ATTR_AS.apply(rel.getAlias(), name, getAlias()));
            
        } else {
            
            sb.append("( ");
            internalNode.toString(sb);
            sb.append(") ").append(Token.AS.apply(getAlias()));
        }
    }
}

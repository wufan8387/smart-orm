package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.NodeType;
import org.smart.orm.functions.Func;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;


public class AttributeNode<T extends Statement> extends AbstractSqlNode<T, AttributeNode<T>> {
    
    private String name;
    private String alias;
    
    private SqlNode<T, ?> expNode;
    
    private RelationNode<T> rel;
    
    private Func<String> op = Token.REL_ATTR_AS;
    
    public AttributeNode(String name) {
        this.name = name;
    }
    
    public AttributeNode(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
    
    public AttributeNode(SqlNode<T, ?> expNode, String alias) {
        this.expNode = expNode;
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
    
    
    public Func<String> getOp() {
        return op;
    }
    
    public void setOp(Func<String> op) {
        this.op = op;
    }
    
    @Override
    public int getType() {
        return NodeType.ATTRIBUTE;
    }
    
    
    public RelationNode<T> from(String rel) {
        T statement = statement();
        this.rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<T>(rel).attach(statement)
        );
        attach(statement);
        return this.rel;
    }
    
    public RelationNode<T> from(String rel, String alias) {
        T statement = statement();
        this.rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(rel),
                () -> new RelationNode<T>(rel).attach(statement)
        )
                .setAlias(alias);
        attach(statement);
        return this.rel;
    }
    
    public RelationNode<T> from(RelationNode<T> rel) {
        this.rel = rel;
        attach(statement());
        return rel;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        if (expNode == null) {
            sb.append(Token.REL_ATTR_AS.apply(rel.getAlias(), name, getAlias()));
            
        } else {
            
            sb.append("( ");
            expNode.toString(sb);
            sb.append(") ").append(Token.AS.apply(getAlias()));
        }
    }
}

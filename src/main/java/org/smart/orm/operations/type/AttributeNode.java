package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;

public class AttributeNode<T extends Statement, K extends Model<K>> implements SqlNode<T> {
    
    
    private T statement;
    
    private PropertyGetter<K> name;
    private String alias;
    
    private SqlNode<T> expNode;
    
    private RelationNode<T, K> rel;
    
    private PropertyInfo prop;
    
    public AttributeNode(T statement, PropertyGetter<K> name) {
        this.statement = statement;
        this.name = name;
    }
    
    public AttributeNode(T statement, PropertyGetter<K> name, String alias) {
        this.statement = statement;
        this.name = name;
        this.alias = alias;
    }
    
    public AttributeNode(T statement, SqlNode<T> expNode, String alias) {
        this.statement = statement;
        this.expNode = expNode;
        this.alias = alias;
    }
    
    public String getName() {
        return prop.getColumnName();
    }
    
    public String getAlias() {
        if (StringUtils.isNotEmpty(alias))
            return alias;
        alias = statement.alias(getName());
        return alias;
    }
    
    public AttributeNode<T, K> setAlias(String alias) {
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
    
    public RelationNode<T, K> from(Class<K> cls) {
        rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(Model.getMeta(cls).getTable().getName()),
                () -> statement.attach(new RelationNode<>(statement, cls))
        );
        prop = Model.getMeta(cls).getPropInfo(this.name);
        statement.attach(this);
        
        return rel;
    }
    
    
    public RelationNode<T, K> from(RelationNode<T, K> rel) {
        this.rel = rel;
        prop = Model.getMeta(rel.getRelClass()).getPropInfo(this.name);
        statement.attach(this);
        return rel;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        if (expNode == null) {
            sb.append(Token.ATTR_AS.apply(rel.getAlias(), getName(), getAlias()));
            
        } else {
            
            sb.append("( ");
            expNode.toString(sb);
            sb.append(") ").append(Token.AS.apply(getAlias()));
        }
    }
    
    
}

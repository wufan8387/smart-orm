package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.functions.Func;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;

public class AttributeNode<T extends Statement, K extends Model<K>> implements SqlNode<T> {
    
    
    private T statement;
    
    private PropertyGetter<K> name;
    private String alias;
    
    private SqlNode<T> expNode;
    
    private RelationNode<T, K> rel;
    
    private PropertyInfo prop;
    
    private Func<String> op = Token.REL_ATTR_AS;
    
    public AttributeNode(T statement, RelationNode<T, K> rel, PropertyInfo prop) {
        this.statement = statement;
        this.rel = rel;
        this.prop = prop;
    }
    
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
    
    public RelationNode<T, K> getRel() {
        return rel;
    }
    
    public PropertyInfo getProp() {
        return prop;
    }
    
    public String getName() {
        return prop.getColumnName();
    }
    
    public String getAlias() {
        if (StringUtils.isNotEmpty(alias))
            return alias;
        alias = getName();
        return alias;
    }
    
    public AttributeNode<T, K> setAlias(String alias) {
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
    
    @Override
    public T statement() {
        return statement;
    }
    
    public RelationNode<T, K> from(Class<K> cls) {
        rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(Model.getMeta(cls).getTable().getName()),
                () -> new RelationNode<>(statement, cls)
        );
        prop = Model.getMeta(cls).getPropInfo(this.name);
        statement.attach(this);
        
        return rel;
    }
    
    
    public RelationNode<T, K> from(RelationNode<T, K> rel) {
        this.rel = rel;
        prop = Model
                .getMeta(rel.getEntityInfo().getEntityClass())
                .getPropInfo(this.name);
        statement.attach(this);
        return rel;
    }
    
    @Override
    public void toString(StringBuilder sb) {
        if (expNode == null) {
            sb.append(op.apply(rel.getAlias(), getName(), getAlias()));
            
        } else {
            
            sb.append("( ");
            expNode.toString(sb);
            sb.append(") ").append(Token.AS.apply(getAlias()));
        }
    }
    
    
    @Override
    public int hashCode() {
        return getAlias().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (!(obj instanceof AttributeNode))
            return false;
        
        AttributeNode data = (AttributeNode) obj;
        
        return data.getAlias().equals(getAlias());
        
        
    }
    
    
}

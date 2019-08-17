package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.functions.Func;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.reflect.PropertyInfo;

public class AttributeNode<T extends Statement, K extends Model<K>> extends AbstractSqlNode<T, AttributeNode<T, K>> {
    
    
    private PropertyGetter<K> name;
    private String alias;
    
    private SqlNode<T, ?> expNode;
    
    private RelationNode<T, K> rel;
    
    private PropertyInfo prop;
    
    private Func<String> op = Token.REL_ATTR_AS;
    
    public AttributeNode(RelationNode<T, K> rel, PropertyInfo prop) {
        this.rel = rel;
        this.prop = prop;
    }
    
    public AttributeNode(PropertyGetter<K> name) {
        this.name = name;
    }
    
    public AttributeNode(PropertyGetter<K> name, String alias) {
        this.name = name;
        this.alias = alias;
    }
    
    public AttributeNode(SqlNode<T, ?> expNode, String alias) {
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
    
    
    public RelationNode<T, K> from(Class<K> cls) {
        T statement = statement();
        rel = statement.findFirst(NodeType.RELATION,
                t -> t.getName().equals(Model.getMetaManager().findEntityInfo(cls).getTableName()),
                () -> new RelationNode<T, K>(cls).attach(statement)
        );
        prop = Model.getMetaManager().findEntityInfo(cls).getProp(this.name);
        attach(statement);
        return rel;
    }
    
    
    public RelationNode<T, K> from(RelationNode<T, K> rel) {
        this.rel = rel;
        prop = rel.getEntityInfo().getProp(this.name);
        attach(statement());
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

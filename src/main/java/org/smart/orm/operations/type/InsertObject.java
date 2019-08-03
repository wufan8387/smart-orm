package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.annotations.IdType;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractStatement;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.ValuesNode;
import org.smart.orm.reflect.PropertyInfo;

import java.util.*;
import java.util.stream.Collectors;

public class InsertObject<T extends Model<T>> extends AbstractStatement {
    
    private RelationNode<InsertObject<T>, ?> relRoot;
    
    
    private Map<String, PropertyInfo> propMap;
    
    
    @Override
    public StatementType getType() {
        return StatementType.DML;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <K extends SqlNode<?>> void doAttach(K node) {
    }
    
    
    public InsertObject<T> into(Class<T> cls) {
        relRoot = new RelationNode<>(this, cls);
        propMap = relRoot.getEntityInfo()
                .getPropList()
                .stream()
                .filter(t -> t.getIdType() != IdType.Auto)
                .collect(Collectors.toMap(PropertyInfo::getColumnName, t -> t));
        return this;
    }
    
    public InsertObject<T> values(T... data) {
        
        
        for (T obj : data) {
            Map<String, PropertyGetter<T>> changeMap = obj.getChangeMap();
            
            List<Object> dataList = new ArrayList<>();
            
            for (String key : propMap.keySet()) {
                PropertyInfo prop = propMap.get(key);
                
                PropertyGetter<T> getter = changeMap.get(prop.getPropertyName());
                if (getter == null) {
                    dataList.add(null);
                } else {
                    dataList.add(getter.apply(obj));
                }
                
                
            }
            
            attach(new ValuesNode<InsertObject>(this, dataList.toArray()));
            
        }
        
        return this;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.getParams().clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.INSERT_INTO.apply(relRoot.getName()));
        
        List<AttributeNode<InsertObject<T>, ?>> attrList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<InsertObject<T>, ?>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        
        if (attrSize == 0) {
            
            for (String key : propMap.keySet()) {
                PropertyInfo prop = propMap.get(key);
                AttributeNode<InsertObject<T>, ?> attrNode = new AttributeNode<>(this, relRoot, prop);
                attrNode.setOp(Token.ATTR_INSERT);
                attrList.add(attrNode);
                attach(attrNode);
            }
        }
        
        
        attrSize = attrList.size();
        
        sb.append("(");
        for (int i = 0; i < attrSize; i++) {
            AttributeNode<InsertObject<T>, ?> node = attrList.get(i);
            node.setAlias(node.getName());
            node.toString(sb);
            if (i < attrSize - 1)
                sb.append(",");
        }
        sb.append(")");
        
        
        sb.append(Token.VALUES);
        
        List<ValuesNode<InsertObject>> valuesList = getNodes()
                .stream().filter(t -> t.getType() == NodeType.VALUES)
                .map(t -> (ValuesNode<InsertObject>) t)
                .collect(Collectors.toList());
        
        int valSize = valuesList.size();
        for (int i = 0; i < valSize; i++) {
            ValuesNode<InsertObject> node = valuesList.get(i);
            node.toString(sb);
            if (i < valSize - 1)
                sb.append(",");
        }
        
        return sb.toString();
    }
    
    
}

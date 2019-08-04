package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.IdType;
import org.smart.orm.annotations.Table;

import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.text.ValuesNode;
import org.smart.orm.operations.type.*;
import org.smart.orm.reflect.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

public abstract class Model<T extends Model<T>> {
    
    private final static Map<String, EntityInfo> metaMap = new HashMap<>();
    
    private Map<String, PropertyGetter<T>> changeMap = new HashMap<>();
    
    public static Map<String, EntityInfo> getMetaMap() {
        return metaMap;
    }
    
    private EntityInfo entityInfo;
    
    public Map<String, PropertyGetter<T>> getChangeMap() {
        return changeMap;
    }
    
    public EntityInfo getMeta() {
        if (entityInfo == null) {
            Class<?> cls = this.getClass();
            String clsName = cls.getName();
            synchronized (metaMap) {
                entityInfo = metaMap.get(clsName);
                if (entityInfo == null) {
                    entityInfo = buildEntityInfo(cls);
                    metaMap.put(clsName, entityInfo);
                }
            }
        }
        return entityInfo;
    }
    
    
    @NotNull
    public static EntityInfo getMeta(Class<?> cls) {
        
        String clsName = cls.getName();
        
        synchronized (metaMap) {
            EntityInfo entityInfo = metaMap.get(clsName);
            if (entityInfo == null) {
                entityInfo = buildEntityInfo(cls);
                metaMap.put(clsName, entityInfo);
            }
            return entityInfo;
        }
    }
    
    
    private static EntityInfo buildEntityInfo(Class<?> cls) {
        EntityInfo entityInfo = new EntityInfo();
        
        Table table = cls.getDeclaredAnnotation(Table.class);
        TableInfo tableInfo = new TableInfo(table);
        entityInfo.setTable(tableInfo);
        entityInfo.setEntityClass(cls);
        
        Field[] fieldList = cls.getDeclaredFields();
        for (Field field : fieldList) {
            
            
            Column column = field.getAnnotation(Column.class);
            if (column == null)
                continue;
            field.setAccessible(true);
            PropertyInfo propertyInfo = new PropertyInfo(column, field);
            entityInfo.add(propertyInfo);
        }
        
        return entityInfo;
        
    }
    
    
    protected void propertyChange(String name, PropertyGetter<T> property) {
        changeMap.put(name, property);
    }
    
    @SuppressWarnings("unchecked")
    public void insert(OperationContext context) {
        InsertObject<T> insOp = new InsertObject(this.getClass());
        
        T data = (T) this;
        
        insOp.getModelList().add(data);
        
        List<Object> valueList = new ArrayList<>();
        
        List<PropertyInfo> propList = getMeta().getPropList();
        
        for (PropertyInfo prop : propList) {
            if (prop.getIdType() == IdType.Auto)
                continue;
            PropertyGetter<T> getter = changeMap.get(prop.getPropertyName());
            if (getter == null) {
                valueList.add(null);
            } else {
                valueList.add(getter.apply(data));
            }
        }
        
        insOp.values(valueList.toArray());
        
        context.add(insOp);
    }
    
    
    @SuppressWarnings("unchecked")
    public void delete(OperationContext context) {
        DeleteObject<T> delOp = new DeleteObject(this.getClass());
    
        for (PropertyInfo prop : getMeta().getKeyList()) {
            delOp.where(prop, Op.EQUAL, prop.get(this));
        }
        
        context.add(delOp);
    }
    
    
    @SuppressWarnings("unchecked")
    public void update(OperationContext context) {
        UpdateObject<T> statement = new UpdateObject(this.getClass());
        
        for (String name : changeMap.keySet()) {
            PropertyGetter<T> getter = changeMap.get(name);
            statement.assign(getter, getter.apply((T) this));
        }
        
        
        for (PropertyInfo prop : getMeta().getKeyList()) {
            statement.where(prop, Op.EQUAL, prop.get(this));
        }
        
        context.add(statement);
        
    }
    
    
}

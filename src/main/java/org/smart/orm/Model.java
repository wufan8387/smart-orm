package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.type.*;
import org.smart.orm.reflect.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        InsertObject insOp = new InsertObject();
        insOp.into(this.getClass());
        context.add(insOp);
    }
    
    
    @SuppressWarnings("unchecked")
    public void delete(OperationContext context) {
        DeleteObject delOp = new DeleteObject();
        delOp.from(this.getClass());
        context.add(delOp);
    }
    
    
    @SuppressWarnings("unchecked")
    public void update(OperationContext context) {
        UpdateObject upOp = new UpdateObject();
        upOp.update(this.getClass());
        
        for (String name : changeMap.keySet()) {
            PropertyGetter<T> getter = changeMap.get(name);
            upOp.set(getter, getter.apply((T) this));
        }
        
        context.add(upOp);
        
    }
    
}

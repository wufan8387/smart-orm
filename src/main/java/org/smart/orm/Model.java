package org.smart.orm;

import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;
import org.smart.orm.operations.DeleteOperation;
import org.smart.orm.operations.InsertOperation;
import org.smart.orm.operations.UpdateOperation;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Model<T> {
    
    
    private final static Map<String, EntityInfo> metaMap = new HashMap<>();
    
    private static Map<Integer, List<ChangeInfo>> changeMap = new HashMap<>();
    
    private List<ChangeInfo> changeList = new ArrayList<>();
    
    public static Map<String, EntityInfo> getMetaMap() {
        return metaMap;
    }
    
    private EntityInfo entityInfo;
    
    public EntityInfo getMeta() {
        if (entityInfo == null) {
            Class cls = this.getClass();
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
    
    
    private EntityInfo buildEntityInfo(Class cls) {
        EntityInfo entityInfo = new EntityInfo();
        
        Table table = (Table) cls.getDeclaredAnnotation(Table.class);
        entityInfo.setTable(table);
        entityInfo.setEntityClass(cls);
        
        Field[] fieldList = cls.getDeclaredFields();
        for (Field field : fieldList) {
            
            Column column = field.getAnnotation(Column.class);
            if (column == null)
                continue;
            
            String propertyName = field.getName();
            
            if (!column.name().equals("")) {
                try {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(column);
                    Field value = invocationHandler.getClass().getDeclaredField("memberValues");
                    value.setAccessible(true);
                    Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
                    memberValues.put("name", propertyName);
                } catch (Exception e) {
                    throw new SmartORMException(e);
                }
            }
            
            
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setColumn(column);
            propertyInfo.setField(field);
            entityInfo.getPropertyMap().put(propertyName, propertyInfo);
            
        }
        
        return entityInfo;
        
    }
    
    protected void propertyChange(String property, Object value) {
        
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.propertyName = property;
        changeInfo.value = value;
        
        changeList.remove(changeInfo);
        changeList.add(changeInfo);
        
    }
    
    protected void propertyChange(Getter<T> property, Object value) {
        String propertyName = LambdaParser.getGet(property).getName();
        this.propertyChange(propertyName, value);
    }
    
    
    public InsertOperation<T> insert() {
        InsertOperation<T> insOp = new InsertOperation<>((T) this);
        return insOp;
    }
    
    public DeleteOperation<T> delete() {
        DeleteOperation<T> delOp = new DeleteOperation<>((T) this);
        return delOp;
    }
    
    public UpdateOperation<T> update() {
        UpdateOperation<T> upOp = new UpdateOperation<>((T) this);
        return upOp;
    }
    
    private static class ChangeInfo {
        
        public String propertyName;
        
        public Object value;
        
        @Override
        public int hashCode() {
            return this.propertyName.hashCode();
        }
        
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return false;
            if (obj.getClass() == this.getClass()) {
                ChangeInfo changeInfo = (ChangeInfo) obj;
                
                if (this.propertyName.equals(changeInfo.propertyName))
                    return true;
            } else {
                if (this.propertyName.equals(obj))
                    return true;
            }
            
            
            return false;
        }
        
    }
    
}

package org.smart.orm.reflect;

import org.smart.orm.SmartORMException;
import org.smart.orm.annotations.*;

import java.lang.reflect.Field;

public class PropertyInfo {
    
    private String columnName;
    
    private Field field;
    
    private Key key;
    
    private EntityInfo entityInfo;
    
    public PropertyInfo() {
    }
    
    public PropertyInfo(EntityInfo entityInfo, Field field, Column column) {
        this.entityInfo = entityInfo;
        this.field = field;
        this.columnName = column.value();
        if (this.columnName.equals("")) {
            this.columnName = this.field.getName();
        }
    }
    
    public boolean isKey() {
        return key != null;
    }
    
    
    public void setKey(Key key) {
        this.key = key;
    }
    
    public String getPropName() {
        return field.getName();
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public EntityInfo getEntityInfo() {
        return entityInfo;
    }
    
    public Field getField() {
        return field;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(Object obj) {
        try {
            return (T) field.get(obj);
        } catch (IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
    }
    
    public void set(Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
    }
    
}

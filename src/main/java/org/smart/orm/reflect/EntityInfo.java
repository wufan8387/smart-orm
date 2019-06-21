package org.smart.orm.reflect;

import org.smart.orm.annotations.Table;

import java.util.HashMap;
import java.util.Map;

public class EntityInfo {
    
    private Class entityClass;
    
    private Table table;
    
    private Map<String, PropertyInfo> propertyMap = new HashMap<>();
    
    public Class getEntityClass() {
        return entityClass;
    }
    
    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }
    
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    
    public Map<String, PropertyInfo> getPropertyMap() {
        return propertyMap;
    }
}

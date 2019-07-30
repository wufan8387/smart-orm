package org.smart.orm.reflect;

import org.smart.orm.SmartORMException;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class EntityInfo {

    private Class<?> entityClass;

    private TableInfo table;

    private Map<String, PropertyInfo> propertyMap = new HashMap<>();

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @NotNull
    public TableInfo getTable() {
        return table;
    }

    public void setTable(TableInfo table) {
        this.table = table;
    }

    public Map<String, PropertyInfo> getPropertyMap() {
        return propertyMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T newInstance() {
        
        try {
            Object obj = this.entityClass.newInstance();
            return (T) obj;
        } catch (Exception ex) {
            throw new SmartORMException(ex);
        }

    }
}

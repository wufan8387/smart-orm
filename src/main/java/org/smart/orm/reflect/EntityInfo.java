package org.smart.orm.reflect;

import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.functions.PropertyGetter;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

public class EntityInfo {
    
    private Class<?> entityClass;
    
    private TableInfo table;
    
    private final Map<String, PropertyInfo> textPropMap = new HashMap<>();
    
    private final Map<Field, PropertyInfo> fieldPropMap = new HashMap<>();
    
    private final List<PropertyInfo> propList = new ArrayList<>();
    
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
    
    public List<PropertyInfo> getPropList() {
        return Collections.unmodifiableList(propList);
    }
    
    public void add(PropertyInfo prop) {
        textPropMap.putIfAbsent(prop.getPropertyName(), prop);
        fieldPropMap.putIfAbsent(prop.getField(), prop);
        propList.add(prop);
    }
    

    
    public PropertyInfo getPropInfo(String name) {
        return textPropMap.get(name);
    }
    
    public <T extends Model<T>> PropertyInfo getPropInfo(PropertyGetter<T> getter) {
        Field field = LambdaParser.getGetter(getter);
        return fieldPropMap.get(field);
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

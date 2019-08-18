package org.smart.orm.reflect;

import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.annotations.Table;
import org.smart.orm.functions.PropertyGetter;

import javax.xml.soap.SAAJResult;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EntityInfo {
    
    private Class<?> type;
    
    private String tableName;
    
    private final Map<String, PropertyInfo> propMap = new HashMap<>();
    
    private final List<PropertyInfo> propList = new ArrayList<>();
    
    private final List<PropertyInfo> keyPropList = new ArrayList<>();
    
    public EntityInfo(Class<?> type, Table table) {
        this.type = type;
        this.tableName = table.value();
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public Class<?> getType() {
        return type;
    }
    
    public List<PropertyInfo> getPropList() {
        return Collections.unmodifiableList(propList);
    }
    
    public List<PropertyInfo> getKeys() {
        return Collections.unmodifiableList(keyPropList);
    }
    
    public void add(PropertyInfo prop) {
        propMap.putIfAbsent(prop.getField().getName(), prop);
        propList.add(prop);
        if (prop.isKey()) {
            keyPropList.add(prop);
        }
    }
    
    public PropertyInfo getProp(String name) {
        return propMap.get(name);
    }
    
    public <T extends Model<T>> PropertyInfo getProp(PropertyGetter<T> getter) {
        Field field = LambdaParser.getGetter(getter);
        return propMap.get(field.getName());
    }
    
    
    @SuppressWarnings("unchecked")
    public <T> T newInstance() {
        
        try {
            Object obj = this.type.newInstance();
            return (T) obj;
        } catch (Exception ex) {
            throw new SmartORMException(ex);
        }
        
    }
    
    public void setValue(Model<?> obj, PropertyInfo prop, Object value) {
        Arrays.stream(this.type.getDeclaredMethods())
                .filter(t -> t.getName().toUpperCase().equals("SET" + prop.getPropName()))
                .findFirst()
                .ifPresent(t -> {
                    try {
                        t.invoke(obj, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new SmartORMException(e);
                    }
                });
    }
}

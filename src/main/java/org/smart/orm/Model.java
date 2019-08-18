package org.smart.orm;

import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.functions.PropertyListener;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.type.DeleteObject;
import org.smart.orm.operations.type.InsertObject;
import org.smart.orm.operations.type.QueryObject;
import org.smart.orm.operations.type.UpdateObject;
import org.smart.orm.reflect.*;

import java.util.*;
import java.util.function.Supplier;

public abstract class Model<T extends Model<T>> {
    
    
    private Map<String, PropertyGetter<T>> changeMap = new HashMap<>();
    
    
    private EntityInfo entityInfo;
    
    private List<PropertyListener> listenerList = new ArrayList<>();
    
    private static MetaManager metaManager = new MetaManager();
    
    public Map<String, PropertyGetter<T>> getChangeMap() {
        return changeMap;
    }
    
    public EntityInfo getMeta() {
        if (entityInfo == null) {
            entityInfo = metaManager.findEntityInfo(this.getClass());
        }
        return entityInfo;
    }
    
    public static MetaManager getMetaManager() {
        return metaManager;
    }
    
    public void insert(OperationContext context) {
        InsertObject<T> statement = buildInsertStatement();
        context.add(this, statement);
    }
    
    public void insert(Executor executor) {
        InsertObject<T> statement = buildInsertStatement();
        statement.execute(executor);
        ResultData<Map<String, Object>> resultData = statement.getResult();
        if (resultData.getCount() > 0) {
            Object[] keyData = resultData.all().get(0).values().toArray();
            metaManager.fillAutoGenerateKeys(this, keyData);
        }
        changeMap.clear();
    }
    
    public void delete(OperationContext context) {
        DeleteObject<T> statement = buildDeleteStatement();
        context.add(this, statement);
    }
    
    public void delete(Executor executor) {
        DeleteObject<T> statement = buildDeleteStatement();
        statement.execute(executor);
        changeMap.clear();
    }
    
    public void update(OperationContext context) {
        UpdateObject<T> statement = buildUpdateStatement();
        context.add(this, statement);
    }
    
    public void update(Executor executor) {
        UpdateObject<T> statement = buildUpdateStatement();
        statement.execute(executor);
        changeMap.clear();
    }
    
    public static <K extends Model<K>> QueryObject<K> query(Class<K> cls) {
        return new QueryObject<>(cls);
    }
    
    
    public void addListener(PropertyListener listener) {
        listenerList.add(listener);
    }
    
    @SuppressWarnings("unchecked")
    protected void propertyChange(String name, PropertyGetter<T> property) {
        changeMap.put(name, property);
        Object val = property.apply((T) this);
        listenerList.forEach(t -> t.onChange(this, name, val));
    }
    
    
    @SuppressWarnings("unchecked")
    private InsertObject<T> buildInsertStatement() {
        
        InsertObject<T> statement = new InsertObject<>((Class<T>) this.getClass());
        
        T data = (T) this;
        
        List<PropertyInfo> propList = getMeta().getPropList();
        
        Supplier<Object> supplier = () -> {
            List<Object> valueList = new ArrayList<>();
            for (PropertyInfo prop : propList) {
                if (prop.isKey() && !changeMap.containsKey(prop.getField().getName())) {
                    continue;
                }
                PropertyGetter<T> getter = changeMap.get(prop.getField().getName());
                statement.attributes(prop);
                if (getter == null) {
                    valueList.add(null);
                } else {
                    valueList.add(getter.apply(data));
                }
            }
            return valueList.toArray();
        };
        
        statement.values(supplier);
        return statement;
    }
    
    @SuppressWarnings("unchecked")
    private DeleteObject<T> buildDeleteStatement() {
        DeleteObject<T> statement = new DeleteObject<>((Class<T>) this.getClass());
        
        for (PropertyInfo prop : getMeta().getKeys()) {
            statement.where(prop, Op.EQUAL, prop.get(this));
        }
        
        return statement;
    }
    
    @SuppressWarnings("unchecked")
    private UpdateObject<T> buildUpdateStatement() {
        
        
        UpdateObject<T> statement = new UpdateObject<>((Class<T>) this.getClass());
        
        for (String name : changeMap.keySet()) {
            PropertyGetter<T> getter = changeMap.get(name);
            statement.assign(getter, getter.apply((T) this));
        }
        
        
        for (PropertyInfo prop : getMeta().getKeys()) {
            statement.where(prop, Op.EQUAL, prop.get(this));
        }
        return statement;
    }

    
    
}

package org.smart.orm.execution;

import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.jdbc.ResultTypeHandler;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.PropertyInfo;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ObjectHandler<T extends Model<T>> implements ResultHandler<T> {
    
    private ResultTypeHandler resultTypeHandler = new ResultTypeHandler();
    
    private List<T> result = new ArrayList<>();
    
    private Class<T> entityClass;
    
    private Map<String, PropertyInfo> aliasMap = new HashMap<>();
    
    @Override
    public T getFirst() {
        if (result.size() > 0)
            return result.get(0);
        return null;
    }
    
    @Override
    public List<T> getAll() {
        return result;
    }
    
    public void add(String alias, PropertyInfo prop) {
        aliasMap.putIfAbsent(alias, prop);
    }
    
    public ObjectHandler(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    public void handle(ResultSet resultset) {
        
        
        EntityInfo entityInfo = Model.getMeta(entityClass);
        
        List<String> nameList = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultset.getMetaData();
            
            for (int i = 0, n = metaData.getColumnCount(); i < n; i++) {
                nameList.add(metaData.getColumnLabel(i + 1));
            }
            while (resultset.next()) {
                
                T data = entityInfo.newInstance();
                
                for (int i = 0, n = nameList.size(); i < n; i++) {
                    String name = nameList.get(i);
                    PropertyInfo prop = aliasMap.get(name);
                    if (prop == null)
                        continue;
                    Field field = prop.getField();
                    Class<?> cls = field.getType();
                    Object cellData = resultTypeHandler.handle(cls, resultset, i + 1);
                    field.set(data, cellData);
                }
                
                result.add(data);
            }
            
        } catch (SQLException | IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
        
        
    }
    
    
}

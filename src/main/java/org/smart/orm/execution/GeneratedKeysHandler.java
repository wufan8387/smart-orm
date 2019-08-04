package org.smart.orm.execution;

import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.annotations.IdType;
import org.smart.orm.jdbc.ResultTypeHandler;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.PropertyInfo;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class GeneratedKeysHandler<T extends Model<T>> implements ResultHandler<T> {
    
    private ResultTypeHandler resultTypeHandler = new ResultTypeHandler();
    
    private List<T> result = new ArrayList<>();
    
    
    private List<PropertyInfo> propList;
    
    public GeneratedKeysHandler(EntityInfo entityInfo) {
        propList = entityInfo
                .getKeyList()
                .stream().filter(t -> t.getIdType() == IdType.Auto)
                .collect(Collectors.toList());
    }
    
    public int autoGenerateKeys() {
        if (propList.size() > 0)
            return Statement.RETURN_GENERATED_KEYS;
        return Statement.NO_GENERATED_KEYS;
    }
    
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
    
    @Override
    public void handle(ResultSet resultset) {
        
        try {
            ResultSetMetaData metaData = resultset.getMetaData();
            
            while (resultset.next()) {
                for (int i = 0, n = metaData.getColumnCount(); i < n; i++) {
                    PropertyInfo prop = propList.get(i);
                    if (prop == null)
                        continue;
                    Field field = prop.getField();
                    Class<?> cls = field.getType();
                    Object cellData = resultTypeHandler.handle(cls, resultset, i + 1);
                    T data = result.get(i);
                    field.set(data, cellData);
                }
            }
        } catch (SQLException | IllegalAccessException ex) {
            throw new SmartORMException(ex);
        }
        
    }
    
    
}

package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyInfo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SelectOperation<T> {
    
    private Integer limit;
    
    private List<String> columnList = new ArrayList<>();
    private List<WhereOperation> whereList = new ArrayList<>();
    
    public SelectOperation(EntityInfo entityInfo) {
    
    }
    
    public SelectOperation<T> include(String... properties) {
        return this;
    }
    
    public SelectOperation<T> include(Getter<T>... properties) {
        return this;
    }
    
    public SelectOperation<T> where(WhereOperation<T>... operations) {
        for (WhereOperation<T> operation : operations) {
            whereList.add(operation);
        }
        return this;
    }
    
    public SelectOperation<T> limit(Integer count) {
        this.limit = count;
        return this;
    }
    
    public SelectOperation<T> orderby(OrderbyType orderbyType, String... properties) {
        return this;
    }
    
    public SelectOperation<T> orderby(OrderbyType orderbyType, Getter<T>... properties) {
        return this;
    }
    
    public SelectOperation<T> columns(String... properties) {
        
        for (String property : properties) {
            this.columnList.add(property);
        }
        
        return this;
    }
    
    public SelectOperation<T> columns(Getter<T>... properties) {
        
        for (Getter<T> property : properties) {
            Field field = LambdaParser.getGet(property);
            this.columnList.add(field.getName());
        }
        
        return this;
    }
    
    public List<String> columns() {
        return columnList;
    }
    
    
    private String build() {
        
        
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class cls = (Class) type.getActualTypeArguments()[0];
            EntityInfo entityInfo = Model.getMetaMap().get(cls.getName());
            
            StringBuilder columnsSb = new StringBuilder();
            if (columnList.size() > 0) {
                columnList.forEach(t -> columnsSb.append(String.format(" `%s`, ", t)));
                columnsSb.delete(columnsSb.length() - 2, columnsSb.length());
            } else {
                columnsSb.append(" * ");
            }
            
            String tableName = entityInfo.getTable().name();
            
            
            StringBuilder whereSb = new StringBuilder();
            if (whereList.size() > 0) {
            
            }
            
            StringBuilder orderbySb = new StringBuilder();
            
            
            String select = String.format("select %s from %s %s"
                    , columnsSb.toString()
                    , tableName
                    , whereSb.toString()
                    , orderbySb.toString());
            
            return select;
            
        } catch (Exception e) {
            throw new SmartORMException(e);
        }
        
        
    }
    
    public T execute() {
        throw new NotImplementedException();
    }
    
}




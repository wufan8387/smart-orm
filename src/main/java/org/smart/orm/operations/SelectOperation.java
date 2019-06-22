package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



public class SelectOperation<T> implements Operation {
    
    private Integer limit;
    
    private List<SelectColumn> columnList = new ArrayList<>();
    
    private OperationContext context;
    
    public SelectOperation(OperationContext context) {
        this.context = context;
    }
    
    
    public SelectOperation<T> alias(String property, String alias) {
        this.columnList.add(new SelectColumn(property, alias));
        return this;
    }
    
    public SelectOperation<T> alias(Getter<T> property, String alias) {
        Field field = LambdaParser.getGet(property);
        this.columnList.add(new SelectColumn(field.getName(), alias));
        return this;
    }
    
    public SelectOperation<T> column(String property, String alias) {
        this.columnList.add(new SelectColumn(property, alias));
        return this;
    }
    
    public SelectOperation<T> column(Getter<T> property, String alias) {
        Field field = LambdaParser.getGet(property);
        this.columnList.add(new SelectColumn(field.getName(), alias));
        return this;
    }
    
    public SelectOperation<T> columns(String... properties) {
        
        for (String property : properties) {
            this.columnList.add(new SelectColumn(property));
        }
        
        return this;
    }
    
    public SelectOperation<T> columns(Getter<T>... properties) {
        
        for (Getter<T> property : properties) {
            Field field = LambdaParser.getGet(property);
            this.columnList.add(new SelectColumn(field.getName()));
        }
        
        return this;
    }
    
    public List<SelectColumn> columns() {
        return columnList;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public static class SelectColumn {
        
        public String property;
        
        public String alias;
        
        public SelectColumn(String property) {
            this.property = property;
        }
        
        public SelectColumn(String property, String alias) {
            this.property = property;
            this.alias = alias;
        }
        
        
    }
    
}






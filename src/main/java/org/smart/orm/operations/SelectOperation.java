package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.SelectColumn;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.TableInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SelectOperation<T> implements Operation {
    
    
    private List<SelectColumn> columnList = new ArrayList<>();
    
    private OperationContext context;
    
    public SelectOperation(OperationContext context) {
        this.context = context;
        context.add(this);
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
    
    public SelectOperation<T> column(Operation operation, String alias) {
        this.columnList.add(new SelectColumn(operation, alias));
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
    
    
    public Collection<SelectColumn> columns() {
        return columnList;
    }
    
    
    public FromOperation<T> from() {
        return new FromOperation<>(context);
    }
    
    public FromOperation<T> from(String table) {
        return new FromOperation<>(context, table);
    }
    
    public FromOperation<T> from(TableInfo table) {
        return new FromOperation<>(context, table);
    }
    
    
    public WhereOperation<T> where(WhereOperation<T> operation) {
        operation.setContext(context);
        context.add(operation);
        return operation;
    }
    
    public SelectOperation<T> join(JoinOperation operation) {
        operation.setContext(context);
        context.add(operation);
        return this;
    }
    
    
    @Override
    public int priority() {
        return OperationPriority.SELECT;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    
}






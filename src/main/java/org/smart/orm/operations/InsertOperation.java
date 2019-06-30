package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.TableInfo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

public class InsertOperation<T> implements Operation {
    
    
    private final static String EXPRESSION = " INSERT INTO `%s` ";
    
    private String expression;
    
    private OperationContext context;
    private TableInfo tableInfo;
    
    public InsertOperation(OperationContext context, String table) {
        this.context = context;
        this.tableInfo = new TableInfo(table);
    }
    
    public InsertOperation(OperationContext context, TableInfo tableInfo) {
        this.context = context;
        this.tableInfo = tableInfo;
    }
    
    public InsertOperation<T> include(String... properties) {
        return this;
    }
    
    public InsertOperation<T> include(Getter<T>... properties) {
        return this;
    }
    
    public T execute() {
        throw new NotImplementedException();
    }
    
    public void stage() {
    
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.INSERT;
    }
    
    
    @Override
    public String getExpression() {
        return null;
    }
    
    @Override
    public void build() {
    
    }
    
    @Override
    public Collection<Object> getParams() {
        return null;
    }
    
    @Override
    public OperationContext getContext() {
        return null;
    }
}

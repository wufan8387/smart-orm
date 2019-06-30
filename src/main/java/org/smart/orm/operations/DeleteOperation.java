package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.TableInfo;

import java.util.Collection;

public class DeleteOperation<T> implements Operation {
    
    private final static String EXPRESSION = " DELETE FROM `%s` ";
    
    private String expression;
    
    private OperationContext context;
    
    private TableInfo tableInfo;
    
    
    public DeleteOperation(OperationContext context, String table) {
        this.context = context;
        this.tableInfo = new TableInfo(table);
    }
    
    public DeleteOperation(OperationContext context, TableInfo tableInfo) {
        this.context = context;
        this.tableInfo = tableInfo;
    }
    
    
    public DeleteOperation<T> where(WhereOperation<T>... operations) {
        return this;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.DELETE;
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        expression = String.format(EXPRESSION, tableInfo.getName());
    }
    
    @Override
    public Collection<Object> getParams() {
        return null;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
}

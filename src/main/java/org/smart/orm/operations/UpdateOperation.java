package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.TableInfo;


public class UpdateOperation<T> implements Operation {
    
    private TableInfo tableInfo;
    
    public UpdateOperation(OperationContext context) {
    
    }
    
    public UpdateOperation(OperationContext context, T entity) {
    
    }
    

    
    public UpdateOperation<T> where(WhereOperation<T>... operations) {
        return this;
    }
    
    
    public UpdateOperation<T> set(Getter<T> property, Object value) {
        
        return this;
    }
    
    public UpdateOperation<T> set(String property, Object value) {
        return this;
    }
    
    public void execute() {
    
    }
    
    public void stage() {
    
    }
    
    
    @Override
    public int priority() {
        return OperationPriority.UPDATE;
    }
    
    @Override
    public OperationContext getContext() {
        return null;
    }
}

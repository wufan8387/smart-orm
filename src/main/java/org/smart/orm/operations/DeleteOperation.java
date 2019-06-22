package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.EntityInfo;

public class DeleteOperation<T> implements Operation {
    
    private OperationContext context;
    
    public DeleteOperation(OperationContext context, T entity) {
        this.context = context;
    }
    
    public DeleteOperation(OperationContext context, EntityInfo entityInfo) {
        this.context = context;
    }
    
    
    public DeleteOperation<T> where(WhereOperation<T>... operations) {
        return this;
    }
    
    
    public void execute() {
    
    }
    
    public void stage() {
    
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
}

package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.Getter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InsertOperation<T> implements Operation {
    
    
    public InsertOperation(OperationContext context, T entity) {
    
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
    public OperationContext getContext() {
        return null;
    }
}

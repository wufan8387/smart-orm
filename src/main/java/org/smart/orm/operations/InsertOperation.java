package org.smart.orm.operations;

import org.smart.orm.reflect.Getter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InsertOperation<T> {
    
    public InsertOperation(T entity) {
    
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
    
    public void stage(){
    
    }
    
}

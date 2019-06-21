package org.smart.orm.operations;

import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;

public class UpdateOperation<T> {
    
    public UpdateOperation(T entity) {
    
    }
    
    public UpdateOperation(EntityInfo entityInfo) {
    
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
    
    public void stage(){
    
    }
    
}

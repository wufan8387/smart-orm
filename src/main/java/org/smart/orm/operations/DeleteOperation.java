package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.reflect.EntityInfo;

public class DeleteOperation<T> {
    
    public DeleteOperation(T entity) {
    
    }
    
    public DeleteOperation(EntityInfo entityInfo) {
    
    }
    
    public DeleteOperation<T> where(WhereOperation<T>... operations) {
        return this;
    }
    
    
    public void execute() {
    
    }
    
    public void stage(){
    
    }
    
}

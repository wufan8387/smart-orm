package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;

import java.lang.reflect.ParameterizedType;

public class UpdateOperation<T> implements Operation {
    
    private EntityInfo entityInfo;
    
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
    
    private EntityInfo getEntityInfo() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class cls = (Class) type.getActualTypeArguments()[0];
        return Model.getMetaMap().get(cls.getName());
        
    }
    
    @Override
    public OperationContext getContext() {
        return null;
    }
}

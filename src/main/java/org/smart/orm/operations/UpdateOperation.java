package org.smart.orm.operations;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.Getter;

public class UpdateOperation<T> extends AbstractOperation {
    
    // private TableInfo tableInfo;
    
    private String expression;
    
    public UpdateOperation(OperationContext context) {
    
    }
    
    public UpdateOperation(OperationContext context, T entity) {
    
    }
    
    public UpdateOperation<T> where(WhereOperation<?>... operations) {
        return this;
    }
    
    public UpdateOperation<T> set(Getter<T> property, Object value) {
        
        return this;
    }
    
    public UpdateOperation<T> set(String property, Object value) {
        return this;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.UPDATE;
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
    
    }
    
}

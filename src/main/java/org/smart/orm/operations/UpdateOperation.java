package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.PropertyGetter;

public class UpdateOperation<T extends Model<T>> extends AbstractOperation<T> {
    
    
    private String expression;
    
    public UpdateOperation(OperationContext context) {
    
    }
    
    public UpdateOperation(OperationContext context, T entity) {
    
    }
    
    public UpdateOperation<T> where(WhereOperation<?>... operations) {
        return this;
    }
    
    public UpdateOperation<T> set(PropertyGetter<T> property, Object value) {
        
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

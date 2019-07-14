package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.PropertyGetter;

public class UpdateOperation extends AbstractOperation {
    
    
    private String expression;
    
    public UpdateOperation(OperationContext context) {
    
    }
    
    public UpdateOperation(OperationContext context, String table) {
    
    }
    
    public UpdateOperation(OperationContext context, String table, String alias) {
    
    }
    
    
    public UpdateOperation where(WhereOperation... operations) {
        return this;
    }
    
    public UpdateOperation set(String property, Object value) {
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

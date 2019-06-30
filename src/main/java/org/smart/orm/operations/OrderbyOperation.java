package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;

public class OrderbyOperation<T> implements Operation {
    
    private OperationContext context;
    
    @Override
    public int priority() {
        return OperationPriority.ORDERBY;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public void setContext(OperationContext context) {
        this.context = context;
    }
}

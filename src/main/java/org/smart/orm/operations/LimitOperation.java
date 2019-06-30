package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;

public class LimitOperation implements Operation {
    
    private OperationContext context;
    
    private int limit;
    
    public LimitOperation(OperationContext context,int limit){
        this.context=context;
        context.add(this);
        this.limit=limit;
    }
    
    public int getLimit() {
        return limit;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
}

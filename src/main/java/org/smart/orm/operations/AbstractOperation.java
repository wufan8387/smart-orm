package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractOperation implements Operation {
    
    private UUID batch;
    
    protected List<Object> params = new ArrayList<>();
    
    protected OperationContext context;
    
    @Override
    public UUID getBatch() {
        return batch;
    }
    
    @Override
    public void setBatch(UUID batch) {
        this.batch = batch;
    }
    
    
    @Override
    public List<Object> getParams() {
        return params;
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public void setContext(OperationContext context) {
        this.context = context;
    }
}

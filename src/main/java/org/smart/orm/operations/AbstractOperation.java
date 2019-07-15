package org.smart.orm.operations;

import org.smart.orm.OperationContext;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractOperation implements Operation {
    
    protected UUID batch;
    
    protected List<Object> params = new ArrayList<>();
    
    protected OperationContext context;
    
    protected TableInfo tableInfo;
    
    protected final List<Operation> children = new ArrayList<>();
    
    
    private final UUID id = UUID.randomUUID();
    
    @Override
    public UUID getId() {
        return id;
    }
    
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
    
    @Override
    public void setContext(OperationContext context) {
        this.context = context;
    }
    
    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }
    
    @Override
    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
    
    public List<Operation> getChildren() {
        return children;
    }
    
    @Override
    public void build(StringBuilder sb) {
    
    }
}

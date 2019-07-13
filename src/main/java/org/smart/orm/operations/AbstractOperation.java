package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractOperation<T extends Model<T>> implements Operation<T> {
    
    protected UUID batch = UUID.randomUUID();
    
    protected List<Object> params = new ArrayList<>();
    
    protected OperationContext context;
    
    protected TableInfo tableInfo;
    
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
    
    
}

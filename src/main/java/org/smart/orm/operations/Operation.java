package org.smart.orm.operations;

import org.smart.orm.OperationContext;
import org.smart.orm.reflect.TableInfo;

import java.util.List;
import java.util.UUID;

public interface Operation {
    
    UUID getBatch();
    
    void setBatch(UUID batch);
    
    int getPriority();
    
    String getExpression();
    
    void build();
    
    List<Object> getParams();
    
    OperationContext getContext();
    
    void setContext(OperationContext context);
    
    TableInfo getTableInfo();
    
    void setTableInfo(TableInfo tableInfo);
    
    
}

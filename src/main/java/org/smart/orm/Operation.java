package org.smart.orm;

import java.util.Collection;

public interface Operation {
    
    int getPriority();
    
    String getExpression();
    
    void build();
    
    Collection<Object> getParams();
    
    OperationContext getContext();
    
}

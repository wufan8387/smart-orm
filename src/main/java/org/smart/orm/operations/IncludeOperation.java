package org.smart.orm.operations;

import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.Getter;

public class IncludeOperation extends AbstractOperation {
    
    public SelectOperation<?> include(String... properties) {
        return null;
    }
    
    public <T> SelectOperation<T> include(Getter<?>... properties) {
        return null;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.INCLUDE;
    }
    
    @Override
    public String getExpression() {
        return null;
    }
    
    @Override
    public void build() {
    
    }
    
}

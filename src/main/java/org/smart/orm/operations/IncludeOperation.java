package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.Getter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IncludeOperation implements Operation {
    
    public SelectOperation include(String... properties) {
        throw new NotImplementedException();
    }
    
    public <T> SelectOperation include(Getter<T>... properties) {
        throw new NotImplementedException();
    }
    
    @Override
    public OperationContext getContext() {
        return null;
    }
}

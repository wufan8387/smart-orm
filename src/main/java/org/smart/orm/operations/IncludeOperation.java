package org.smart.orm.operations;

import org.smart.orm.reflect.Getter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IncludeOperation {
    
    public SelectOperation include(String... properties) {
        throw new NotImplementedException();
    }
    
    public <T> SelectOperation include(Getter<T>... properties) {
        throw new NotImplementedException();
    }
    
}

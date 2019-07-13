package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.PropertyGetter;

public class IncludeOperation extends AbstractOperation {
    
    public SelectOperation<?> include(String... properties) {
        return null;
    }
    
    public <T extends Model<T>> SelectOperation<T> include(PropertyGetter<?>... properties) {
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

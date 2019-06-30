package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.JoinType;
import org.smart.orm.reflect.Getter;

public class JoinOperation<T, U> implements Operation {
    
    private OperationContext context;
    
    public JoinOperation(JoinType joinType) {
    
    }

    
    public JoinOperation<T,U> equal(Getter<T> leftProperty, Getter<U> rightProperty){
        return this;
    }
    
    public JoinOperation<T,U> notEqual(Getter<T> leftProperty, Getter<U> rightProperty){
        return this;
    }
    
    
    public JoinOperation<T,U> lessThan(Getter<T> leftProperty, Getter<U> rightProperty){
        return this;
    }
    
    public JoinOperation<T,U> lessEqualThan(Getter<T> leftProperty, Getter<U> rightProperty){
        return this;
    }
    
    
    public JoinOperation<T,U> greatThan(Getter<T> leftProperty, Getter<U> rightProperty){
        return this;
    }
    
    public JoinOperation<T,U> greatEqualThan(Getter<T> leftProperty, Getter<U> rightProperty){
        return this;
    }
    
    
    public JoinOperation<T,U> equal(String leftProperty, String rightProperty){
        return this;
    }
    
    public JoinOperation<T,U> notEqual(String leftProperty, String rightProperty){
        return this;
    }
    
    
    public JoinOperation lessThan(String leftProperty, String rightProperty){
        return this;
    }
    
    public JoinOperation<T,U> lessEqualThan(String leftProperty, String rightProperty){
        return this;
    }
    
    
    public JoinOperation<T,U> greatThan(String leftProperty, String rightProperty){
        return this;
    }
    
    public JoinOperation<T,U> greatEqualThan(String leftProperty, String rightProperty){
        return this;
    }
    
    
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public void setContext(OperationContext context) {
        this.context = context;
    }
}

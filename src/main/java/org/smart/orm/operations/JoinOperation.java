package org.smart.orm.operations;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.Getter;

public class JoinOperation<T, U> extends AbstractOperation {
    
    
    private String expression;
    
    public JoinOperation(JoinType joinType) {
    
    }
    
    public JoinOperation<T, U> equal(Getter<T> leftProperty, Getter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> notEqual(Getter<T> leftProperty, Getter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> lessThan(Getter<T> leftProperty, Getter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> lessEqualThan(Getter<T> leftProperty, Getter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> greatThan(Getter<T> leftProperty, Getter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> greatEqualThan(Getter<T> leftProperty, Getter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> equal(String leftProperty, String rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> notEqual(String leftProperty, String rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> lessThan(String leftProperty, String rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> lessEqualThan(String leftProperty, String rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> greatThan(String leftProperty, String rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> greatEqualThan(String leftProperty, String rightProperty) {
        return this;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.JOIN;
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        expression = "";
    }
    
}

package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.PropertyGetter;

public class JoinOperation<T extends Model<T>, U extends Model<U>> extends AbstractOperation {
    
    
    private String expression;
    
    public JoinOperation(JoinType joinType) {
    
    }
    
    public JoinOperation<T, U> equal(PropertyGetter<T> leftProperty, PropertyGetter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> notEqual(PropertyGetter<T> leftProperty, PropertyGetter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> lessThan(PropertyGetter<T> leftProperty, PropertyGetter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> lessEqualThan(PropertyGetter<T> leftProperty, PropertyGetter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> greatThan(PropertyGetter<T> leftProperty, PropertyGetter<U> rightProperty) {
        return this;
    }
    
    public JoinOperation<T, U> greatEqualThan(PropertyGetter<T> leftProperty, PropertyGetter<U> rightProperty) {
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

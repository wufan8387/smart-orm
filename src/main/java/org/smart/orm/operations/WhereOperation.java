package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyInfo;

import java.util.ArrayList;
import java.util.Collection;


public abstract class WhereOperation<T> implements Operation {
    
    protected WhereType whereType = WhereType.NONE;
    
    protected PropertyInfo propertyInfo;
    
    protected String property;
    
    protected String expression;
    
    protected Collection<Object> params = new ArrayList<>();
    
    protected OperationContext context;
    
    public WhereOperation() {
    }
    
    
    public WhereOperation(String property) {
    
    }
    
    public WhereOperation(WhereType whereType, String property) {
        this.whereType = whereType;
    }
    
    public WhereOperation(WhereType whereType, Getter<T> property) {
        this.whereType = whereType;
    }
    
    public WhereType getWhereType() {
        return whereType;
    }
    
    public void setWhereType(WhereType whereType) {
        this.whereType = whereType;
    }
    
    public String getProperty() {
        return property;
    }
    
    public void setProperty(String property) {
        this.property = property;
    }
    
    public void setProperty(Getter<T> property) {
        this.property = LambdaParser.getGet(property).getName();
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public Collection<Object> getParams() {
        return params;
    }
    
    @Override
    public void build() {
        build(propertyInfo);
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public void setContext(OperationContext context) {
        this.context = context;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.WHERE;
    }
    
    public <U> WhereOperation<U> and(WhereOperation<U> operation) {
        operation.setWhereType(WhereType.AND);
        this.context.add(operation);
        return operation;
    }
    
    public <U> WhereOperation<U> or(WhereOperation<U> operation) {
        operation.setWhereType(WhereType.OR);
        this.context.add(operation);
        return operation;
    }
    
    public LimitOperation limit(int count) {
        return new LimitOperation(this.context, count);
    }
    
    protected abstract void build(PropertyInfo propertyInfo);
    
    
    protected String whereText() {
        switch (whereType) {
            case AND:
                return " and ";
            case OR:
                return " or ";
        }
        return "";
    }
    
}

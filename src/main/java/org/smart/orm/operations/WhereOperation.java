package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.SmartORMException;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyInfo;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


public abstract class WhereOperation<T> implements Operation {
    
    protected WhereType whereType = WhereType.NONE;
    
    protected String property;
    
    protected String expression;
    
    protected List<Object> params = new ArrayList<>();
    
    protected OperationContext context;
    
    public WhereOperation() {
    }
    
    
    public WhereOperation(WhereType whereType) {
        this.whereType = whereType;
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
    
    public String getExpression() {
        return expression;
    }
    
    public List<Object> getParams() {
        return params;
    }
    
    public void build() {
        
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            Class cls = (Class) type.getActualTypeArguments()[0];
            EntityInfo entityInfo = Model.getMetaMap().get(cls.getName());
            PropertyInfo propertyInfo = entityInfo.getPropertyMap().get(property);
            
            build(propertyInfo);
        } catch (Exception e) {
            throw new SmartORMException(e);
        }
        
        
    }
    
    @Override
    public OperationContext getContext() {
        return context;
    }
    
    public void setContext(OperationContext context) {
        this.context = context;
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

package org.smart.orm.operations;

import com.mysql.cj.jdbc.ha.MultiHostMySQLConnection;
import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.SmartORMException;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class WhereOperation<T> extends AbstractOperation {
    
    
    private EntityInfo entityInfo;
    
    private TableInfo tableInfo;
    
    protected WhereType whereType = WhereType.NONE;
    
    protected PropertyInfo propertyInfo;
    
    protected String property;
    
    protected String expression;
    
    
    public WhereOperation() {
        this.entityInfo = Model.getMeta(this.getClass());
    }
    
    public WhereOperation(String property) {
        this.property = property;
        this.entityInfo = Model.getMeta(this.getClass());
        this.buildPropertyInfo();
    }
    
    public WhereOperation(Getter<T> property) {
        this.entityInfo = Model.getMeta(this.getClass());
        if (entityInfo == null)
            throw new SmartORMException();
        this.tableInfo = entityInfo.getTable();
        this.property = LambdaParser.getGet(property).getName();
        this.buildPropertyInfo();
    }
    
    public WhereOperation(WhereType whereType, String property) {
        this.entityInfo = Model.getMeta(this.getClass());
        this.whereType = whereType;
        this.property = property;
        this.buildPropertyInfo();
        
    }
    
    public WhereOperation(WhereType whereType, Getter<T> property) {
        this.entityInfo = Model.getMeta(this.getClass());
        if (entityInfo == null)
            throw new SmartORMException();
        this.tableInfo = entityInfo.getTable();
        this.whereType = whereType;
        this.property = LambdaParser.getGet(property).getName();
        this.buildPropertyInfo();
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
        this.buildPropertyInfo();
    }
    
    public void setProperty(Getter<T> property) {
        if (entityInfo == null)
            throw new SmartORMException();
        this.property = LambdaParser.getGet(property).getName();
        this.buildPropertyInfo();
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        build(tableInfo, propertyInfo);
    }
    
    protected abstract void build(TableInfo tableInfo, PropertyInfo propertyInfo);
    
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
    
    
    protected String whereText() {
        switch (whereType) {
            case AND:
                return " and ";
            case OR:
                return " or ";
            case NONE:
                return StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }
    
    private void buildPropertyInfo() {
        
        if (entityInfo == null) {
            propertyInfo = new PropertyInfo();
            propertyInfo.setColumn(property);
            propertyInfo.setName(property);
        } else {
            propertyInfo = entityInfo.getPropertyMap().get(property);
        }
        
    }
    
}

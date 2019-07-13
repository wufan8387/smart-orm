package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.SmartORMException;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.*;

public abstract class WhereOperation<T extends Model<T>> extends AbstractOperation<T> {
    
    
    private EntityInfo entityInfo;
    
    protected WhereType whereType = WhereType.NONE;
    
    protected String property;
    
    protected String expression;
    
    public WhereOperation() {
        entityInfo = T.getMeta(this.getClass());
        tableInfo = entityInfo.getTable();
    }
    
    public WhereOperation(String property) {
        entityInfo = T.getMeta(this.getClass());
        this.property = property;
    }
    
    public WhereOperation(PropertyGetter<T> property) {
        entityInfo = T.getMeta(this.getClass());
        tableInfo = entityInfo.getTable();
        this.property = entityInfo
                .getPropertyMap()
                .get(LambdaParser.getGetter(property).getName())
                .getColumnName();
    }
    
    public WhereOperation(WhereType whereType, String property) {
        this.whereType = whereType;
        this.property = property;
    }
    
    public WhereOperation(WhereType whereType, PropertyGetter<T> property) {
        this.tableInfo = Model.getMeta(this.getClass()).getTable();
        this.whereType = whereType;
        this.property = LambdaParser.getGetter(property).getName();
    }
    
    public WhereOperation(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
    
    public WhereOperation(TableInfo tableInfo, String property) {
        this.property = property;
        this.tableInfo = tableInfo;
    }
    
    
    public WhereOperation(TableInfo tableInfo, WhereType whereType, String property) {
        this.tableInfo = tableInfo;
        this.whereType = whereType;
        this.property = property;
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
    
    public void setProperty(PropertyGetter<T> property) {
        if (entityInfo == null)
            throw new SmartORMException();
        this.property = LambdaParser.getGetter(property).getName();
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        build(tableInfo, property);
    }
    
    protected abstract void build(TableInfo tableInfo, String property);
    
    @Override
    public int getPriority() {
        if (whereType == WhereType.NONE)
            return OperationPriority.WHERE;
        return OperationPriority.WHERE_N;
    }
    
    public <U extends Model<U>> WhereOperation<U> andOther(WhereOperation<U> operation) {
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        this.context.add(operation);
        return operation;
    }
    
    public <U extends Model<U>> WhereOperation<U> orOther(WhereOperation<U> operation) {
        operation.setWhereType(WhereType.OR);
        operation.setBatch(batch);
        this.context.add(operation);
        return operation;
    }
    
    public WhereOperation<T> andThis(WhereOperation<T> operation) {
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        operation.setTableInfo(tableInfo);
        this.context.add(operation);
        return operation;
    }
    
    public WhereOperation<T> orThis(WhereOperation<T> operation) {
        operation.setWhereType(WhereType.OR);
        operation.setBatch(batch);
        operation.setTableInfo(tableInfo);
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
                return " where ";
        }
        return StringUtils.EMPTY;
    }
    
    
}

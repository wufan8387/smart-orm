package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class WhereOperation<T extends Model<T>> extends AbstractOperation {
    
    
    protected WhereType whereType = WhereType.NONE;
    
    protected String property;
    
    protected String expression;
    
    private List<WhereOperation<?>> children = new ArrayList<>();
    
    public WhereOperation() {
    }
    
    
    public WhereOperation(PropertyGetter<T> property) {
        EntityInfo entityInfo = T.getMeta(this.getClass());
        tableInfo = entityInfo.getTable();
        this.property = entityInfo
                .getPropertyMap()
                .get(LambdaParser.getGetter(property).getName())
                .getColumnName();
    }
    

    
    public WhereOperation(WhereType whereType, PropertyGetter<T> property) {
        EntityInfo entityInfo = T.getMeta(this.getClass());
        this.tableInfo = entityInfo.getTable();
        this.whereType = whereType;
        this.property = entityInfo
                .getPropertyMap()
                .get(LambdaParser.getGetter(property).getName())
                .getColumnName();
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
    
    public void setProperty(PropertyGetter<T> property) {
        EntityInfo entityInfo = T.getMeta(this.getClass());
        this.property = entityInfo
                .getPropertyMap()
                .get(LambdaParser.getGetter(property).getName())
                .getColumnName();
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
    
    public <U extends Model<U>> WhereOperation<U> andFor(WhereOperation<U> operation) {
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        this.children.add(operation);
        return operation;
    }
    
    public <U extends Model<U>> WhereOperation<U> orFor(WhereOperation<U> operation) {
        operation.setWhereType(WhereType.OR);
        operation.setBatch(batch);
        this.context.add(operation);
        return operation;
    }
    
    
    public WhereOperation<?> andFor(String table, WhereOperation<?> operation) {
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        this.children.add(operation);
        return operation;
    }
    
    public WhereOperation<?> orFor(String table, WhereOperation<?> operation) {
        operation.setWhereType(WhereType.OR);
        operation.setBatch(batch);
        this.context.add(operation);
        return operation;
    }
    
    public WhereOperation<T> and(WhereOperation<T> operation) {
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        operation.setTableInfo(tableInfo);
        this.children.add(operation);
        return this;
    }
    
    public WhereOperation<T> or(WhereOperation<T> operation) {
        operation.setWhereType(WhereType.OR);
        operation.setBatch(batch);
        operation.setTableInfo(tableInfo);
        this.children.add(operation);
        return this;
    }
    
    
    public LimitOperation limit(int count) {
        return new LimitOperation(this.context, count);
    }
    
    protected String whereText() {
        switch (whereType) {
            case AND:
                return " AND ";
            case OR:
                return " OR ";
            case WHERE:
                return " WHERE ";
        }
        return StringUtils.EMPTY;
    }
    
    
}

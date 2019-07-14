package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.WhereType;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.*;

import java.util.ArrayList;
import java.util.List;

public abstract class WhereOperation extends AbstractOperation {
    
    
    protected WhereType whereType = WhereType.NONE;
    
    protected String property;
    
    protected String expression;
    
    private List<WhereOperation> children = new ArrayList<>();
    
    public WhereOperation() {
    }
    
    public WhereOperation(WhereType whereType, String property) {
        this.whereType = whereType;
        this.property = property;
    }
    
    public WhereOperation(String table, String property) {
        this.tableInfo = new TableInfo(table);
        this.property = property;
    }
    
    public WhereOperation(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
    
    public WhereOperation(TableInfo tableInfo, String property) {
        this.property = property;
        this.tableInfo = tableInfo;
    }
    
    public WhereOperation(WhereType whereType, TableInfo tableInfo, String property) {
        this.tableInfo = tableInfo;
        this.whereType = whereType;
        this.property = property;
    }
    
    public WhereOperation(WhereType whereType, String table, String property) {
        this.tableInfo = new TableInfo(table);
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
    
    
    public WhereOperation andFor(String table, WhereOperation operation) {
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        this.children.add(operation);
        return operation;
    }
    
    public WhereOperation orFor(String table, WhereOperation operation) {
        operation.setWhereType(WhereType.OR);
        operation.setBatch(batch);
        this.context.add(operation);
        return operation;
    }
    
    public WhereOperation and(WhereOperation operation) {
        operation.setTableInfo(tableInfo);
        operation.setWhereType(WhereType.AND);
        operation.setBatch(batch);
        this.children.add(operation);
        return this;
    }
    
    public WhereOperation or(WhereOperation operation) {
        operation.setWhereType(WhereType.OR);
        operation.setTableInfo(tableInfo);
        operation.setBatch(batch);
        this.children.add(operation);
        return this;
    }
    
    
    public LimitOperation limit(int count) {
        return new LimitOperation(this.context, count);
    }
    
    protected String whereText() {
        switch (whereType) {
            case WHERE:
                return " WHERE ";
            case AND:
                return " AND ";
            case OR:
                return " OR ";
        }
        return StringUtils.EMPTY;
    }
    
    
}

package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractExpression implements Expression {
    
    
    protected List<Object> params = new ArrayList<>();
    
    protected Statement statement;
    
    protected TableInfo tableInfo;
    
    protected final List<Expression> children = new ArrayList<>();
    
    
    private final UUID id = UUID.randomUUID();
    
    @Override
    public UUID getId() {
        return id;
    }
    
    @Override
    public List<Object> getParams() {
        return params;
    }
    
    @Override
    public Statement getStatement() {
        return statement;
    }
    
    @Override
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }
    
    @Override
    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
    
    public List<Expression> getChildren() {
        return children;
    }
    
    @Override
    public void build(StringBuilder sb) {

    }
}

package org.smart.orm.operations;

import org.apache.commons.lang3.NotImplementedException;
import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class InsertOperation<T> extends AbstractOperation {
    
    private final static String EXPRESSION = " INSERT INTO `%s` ";
    
    private String expression;
    
    private OperationContext context;
    private TableInfo tableInfo;
    
    
    public InsertOperation(OperationContext context, String table) {
        this.context = context;
        this.tableInfo = new TableInfo(table);
    }
    
    public InsertOperation(OperationContext context, TableInfo tableInfo) {
        this.context = context;
        this.tableInfo = tableInfo;
    }
    
    public InsertOperation<T> include(String... properties) {
        return this;
    }
    
    public InsertOperation<T> include(Getter<?>... properties) {
        return this;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.INSERT;
    }
    
    @Override
    public String getExpression() {
        return  expression;
    }
    
    @Override
    public void build() {
        expression = String.format(EXPRESSION, tableInfo.getName());
    }

}

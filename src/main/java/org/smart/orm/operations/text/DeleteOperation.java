package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.TableInfo;

public class DeleteOperation extends AbstractOperation {
    
    private final static String EXPRESSION = " DELETE FROM `%s` AS `%s` ";
    
    private String expression;
    
    private TableInfo tableInfo;
    
    
    public DeleteOperation(OperationContext context, String table) {
        this.context = context;
        this.tableInfo = new TableInfo(table);
    }
    
    public DeleteOperation(OperationContext context, String table, String alias) {
        this.context = context;
        this.tableInfo = new TableInfo(table, alias);
    }
    
    public DeleteOperation(OperationContext context, TableInfo tableInfo) {
        this.context = context;
        this.tableInfo = tableInfo;
    }
    
    public WhereOperation where(WhereOperation operation) {
        
        operation.setTableInfo(tableInfo);
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        
        return operation;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.DELETE;
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        expression = String.format(EXPRESSION, tableInfo.getName(), tableInfo.getAlias());
    }
    
    
}

package org.smart.orm.operations.text;

import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.TableInfo;

public class DeleteExpression extends AbstractExpression {
    
    private final static String EXPRESSION = " DELETE FROM `%s` AS `%s` ";
    
    private TableInfo tableInfo;
    
    
    public DeleteExpression(Statement statement, String table) {
        this.statement = statement;
        this.tableInfo = statement.getTable(table);
    }
    
    public DeleteExpression(Statement statement, String table, String alias) {
        this.statement = statement;
        this.tableInfo = statement.getTable(table, alias);
    }
    
    public DeleteExpression(Statement statement, TableInfo table) {
        this.statement = statement;
        this.tableInfo = statement.getTable(table);
    }
    
    public WhereExpression where(WhereExpression operation) {
        
        operation.setTableInfo(tableInfo);
        operation.setStatement(statement);
        statement.add(operation);
        
        return operation;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.DELETE;
    }
    
    
    @Override
    public String build() {
        return String.format(EXPRESSION, tableInfo.getName(), tableInfo.getAlias());
    }
    
    
}

package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DeleteOperation<T> extends AbstractOperation {
    
    private final static String EXPRESSION = " DELETE FROM `%s` ";
    
    private String expression;
    
    private TableInfo tableInfo;
    
    
    public DeleteOperation(OperationContext context, String table) {
        this.context = context;
        this.tableInfo = new TableInfo(table);
    }
    
    public DeleteOperation(OperationContext context, TableInfo tableInfo) {
        this.context = context;
        this.tableInfo = tableInfo;
    }
    
    public DeleteOperation<T> where(WhereOperation<?>... operations) {
        for (WhereOperation<?> operation : operations) {
            operation.setContext(context);
        }
        return this;
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
        expression = String.format(EXPRESSION, tableInfo.getName());
    }
    

}

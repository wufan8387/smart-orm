package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.TableInfo;

public class DeleteOperation<T extends Model<T>> extends AbstractOperation<T> {
    
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
            operation.setBatch(batch);
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

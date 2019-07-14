package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.TableInfo;

public class DeleteOperation<T extends Model<T>> extends AbstractOperation {
    
    private final static String EXPRESSION = " DELETE FROM `%s` ";
    
    private String expression;
    
    private TableInfo tableInfo;
    
    

    public DeleteOperation(OperationContext context) {
        this.context = context;
    }
    
    public DeleteOperation<T> where(WhereOperation<T>... operations) {
        for (WhereOperation<T> operation : operations) {
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

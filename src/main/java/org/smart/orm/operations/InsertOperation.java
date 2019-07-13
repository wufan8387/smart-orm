package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class InsertOperation<T extends Model<T>> extends AbstractOperation<T> {
    
    private final static String EXPRESSION = " INSERT INTO `%s` ";
    
    private String expression;
    
    
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
    
    public InsertOperation<T> include(PropertyGetter<?>... properties) {
        return this;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.INSERT;
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

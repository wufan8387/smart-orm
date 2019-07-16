package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.PropertyGetter;

public class InsertExpression<T extends Model<T>> extends AbstractExpression {
    
    private final static String EXPRESSION = " INSERT INTO `%s` ";
    
    public InsertExpression(Statement statement) {
        this.statement = statement;
    }
    
    public InsertExpression<T> include(PropertyGetter<?>... properties) {
        return this;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.INSERT;
    }
    
    @Override
    public String build() {
        return String.format(EXPRESSION, tableInfo.getName());
    }
    
}

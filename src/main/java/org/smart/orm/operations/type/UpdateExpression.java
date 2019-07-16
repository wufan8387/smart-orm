package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.PropertyGetter;

public class UpdateExpression<T extends Model<T>> extends AbstractExpression {
    
    
    public UpdateExpression(Statement statement) {
    
    }
    
    public UpdateExpression(Statement statement, T entity) {
    
    }
    
    public UpdateExpression<T> where(WhereExpression<?>... operations) {
        return this;
    }
    
    public UpdateExpression<T> set(PropertyGetter<T> property, Object value) {
        
        return this;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.UPDATE;
    }
    
    @Override
    public String build() {
        return StringUtils.EMPTY;
    }
    
}

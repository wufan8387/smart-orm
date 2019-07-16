package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.reflect.PropertyGetter;

public class IncludeExpression extends AbstractExpression {
    
    public SelectExpression<?> include(String... properties) {
        return null;
    }
    
    public <T extends Model<T>> SelectExpression<T> include(PropertyGetter<?>... properties) {
        return null;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.INCLUDE;
    }
    
    @Override
    public String build() {
        return StringUtils.EMPTY;
    }
    
}

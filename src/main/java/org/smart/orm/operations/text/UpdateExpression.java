package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Statement;

public class UpdateExpression extends AbstractExpression {
    
    
    private String expression;
    
    public UpdateExpression(Statement statement) {
    
    }
    
    public UpdateExpression(Statement statement, String table) {
    
    }
    
    public UpdateExpression(Statement statement, String table, String alias) {
    
    }
    
    
    public UpdateExpression where(WhereExpression... operations) {
        return this;
    }
    
    public UpdateExpression set(String property, Object value) {
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

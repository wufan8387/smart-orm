package org.smart.orm.operations.text;

import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Statement;

public class LimitExpression extends AbstractExpression {
    
    private final static String EXPRESSION = " LIMIT %d ";
    
    private int limit;
    
    
    public LimitExpression(Statement statement, int limit) {
        this.statement = statement;
        statement.add(this);
        this.limit = limit;
    }
    
    public int getLimit() {
        return limit;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.LIMIT;
    }
    
    
    @Override
    public String build() {
        return String.format(EXPRESSION, limit);
    }
    
}

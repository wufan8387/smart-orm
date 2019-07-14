package org.smart.orm.operations.text;

import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;

public class LimitOperation extends AbstractOperation {
    
    private final static String EXPRESSION = " LIMIT %d ";
    
    private int limit;
    
    private String expression;
    
    
    public LimitOperation(OperationContext context, int limit) {
        this.context = context;
        context.add(this);
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
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        expression = String.format(EXPRESSION, limit);
    }
    
}

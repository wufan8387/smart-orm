package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.PropertyGetter;

public class JoinOperation extends AbstractOperation {
    
    
    private String expression;
    
    public JoinOperation(JoinType joinType) {
    
    }
    
    public JoinOperation(JoinType joinType, String table) {
    
    }
    
    public JoinOperation(String table) {
    
    }
    
    public <T extends Model<T>, U extends Model<U>> JoinOperation on(PropertyGetter<T> property, WhereOperation<U> condition) {
        return this;
    }
    
    
    public <T extends Model<T>, U extends Model<U>> JoinOperation and(PropertyGetter<T> property, WhereOperation<U> condition) {
        return this;
    }
    
    public <T extends Model<T>, U extends Model<U>> JoinOperation or(PropertyGetter<T> property, WhereOperation<U> condition) {
        return this;
    }
    
    
    public JoinOperation join(JoinOperation operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return operation;
    }
    
    
    public <T extends Model<T>> WhereOperation<T> where(WhereOperation<T> operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return operation;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.JOIN;
    }
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        expression = "";
    }
    
}

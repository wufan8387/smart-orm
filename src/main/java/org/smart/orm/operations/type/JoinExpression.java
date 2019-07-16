package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.reflect.PropertyGetter;

public class JoinExpression extends AbstractExpression {
    
    
    public JoinExpression(JoinType joinType) {
    
    }
    
    public JoinExpression(JoinType joinType, String table) {
    
    }
    
    public JoinExpression(String table) {
    
    }
    
    public <T extends Model<T>, U extends Model<U>> JoinExpression on(PropertyGetter<T> property, WhereExpression<U> condition) {
        return this;
    }
    
    
    public <T extends Model<T>, U extends Model<U>> JoinExpression and(PropertyGetter<T> property, WhereExpression<U> condition) {
        return this;
    }
    
    public <T extends Model<T>, U extends Model<U>> JoinExpression or(PropertyGetter<T> property, WhereExpression<U> condition) {
        return this;
    }
    
    
    public JoinExpression join(JoinExpression operation) {
        operation.setStatement(statement);
        statement.add(operation);
        return operation;
    }
    
    
    public <T extends Model<T>> WhereExpression<T> where(WhereExpression<T> operation) {
        operation.setStatement(statement);
        statement.add(operation);
        return operation;
    }
    
    
    @Override
    public int getPriority() {
        return OperationPriority.JOIN;
    }
    
    
    @Override
    public String build() {
        return StringUtils.EMPTY;
    }
    
}

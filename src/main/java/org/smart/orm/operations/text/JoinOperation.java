package org.smart.orm.operations.text;

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
    
    public JoinOperation(JoinType joinType, String table, String alias) {
    
    }
    
    public JoinOperation(String table) {
    
    }
    
    public JoinOperation(String table, String alias) {
    
    }
    
    
    public JoinOperation on(String property, WhereOperation condition) {
        return this;
    }
    
    public JoinOperation on(String table, String property, WhereOperation condition) {
        return this;
    }
    
    
    public JoinOperation and(String property, WhereOperation condition) {
        return this;
    }
    
    public JoinOperation and(String table, String property, WhereOperation condition) {
        return this;
    }
    
    
    public JoinOperation or(String property, WhereOperation condition) {
        return this;
    }
    
    public JoinOperation or(String table, String property, WhereOperation condition) {
        return this;
    }
    
    
    public JoinOperation join(JoinOperation operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return operation;
    }
    
    
    public WhereOperation where(WhereOperation operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return operation;
    }
    
    public WhereOperation where(String table, WhereOperation operation) {
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

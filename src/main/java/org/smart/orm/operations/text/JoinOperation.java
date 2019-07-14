package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class JoinOperation extends AbstractOperation {
    
    
    private String expression;
    
    private JoinType joinType;
    
    private String thisProperty;
    
    
    public JoinOperation(JoinType joinType, String table) {
        this.joinType = joinType;
        this.tableInfo = new TableInfo(table);
    }
    
    public JoinOperation(JoinType joinType, String table, String alias) {
        this.joinType = joinType;
        this.tableInfo = new TableInfo(table, alias);
    }
    
    public JoinOperation(String table) {
        this.joinType = JoinType.INNER;
        this.tableInfo = new TableInfo(table);
        
    }
    
    public JoinOperation(String table, String alias) {
        this.joinType = JoinType.INNER;
        this.tableInfo = new TableInfo(table, alias);
        
    }
    
    
    public JoinOperation on(String thisProperty, WhereOperation condition) {
        this.thisProperty = thisProperty;
        return this;
    }
    
    
    public JoinOperation and(String thisProperty, WhereOperation condition) {
        return this;
    }
    

    
    public JoinOperation or(String thisProperty, WhereOperation condition) {
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

package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.JoinType;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Formatter;
import org.smart.orm.reflect.TableInfo;

public class JoinExpression extends AbstractExpression {
    
    
    private JoinType joinType;
    
    private String thisProperty;
    
    
    public JoinExpression(JoinType joinType, String table) {
        this.joinType = joinType;
        this.tableInfo = new TableInfo(table);
    }
    
    public JoinExpression(JoinType joinType, String table, String alias) {
        this.joinType = joinType;
        this.tableInfo = new TableInfo(table, alias);
    }
    
    public JoinExpression(String table) {
        this.joinType = JoinType.INNER;
        this.tableInfo = new TableInfo(table);
        
    }
    
    public JoinExpression(String table, String alias) {
        this.joinType = JoinType.INNER;
        this.tableInfo = new TableInfo(table, alias);
        
    }
    
    
    public JoinExpression on(String thisProperty, Formatter exp, String otherTable) {
        this.thisProperty = thisProperty;
        return this;
    }
    
    
    public JoinExpression and(String thisProperty, WhereExpression condition) {
        return this;
    }
    
    
    public JoinExpression or(String thisProperty, WhereExpression condition) {
        return this;
    }
    
    
    public JoinExpression join(JoinExpression operation) {
        operation.setStatement(statement);
        statement.add(operation);
        return operation;
    }
    
    
    public WhereExpression where(WhereExpression operation) {
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

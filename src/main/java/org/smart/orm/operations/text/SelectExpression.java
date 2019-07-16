package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.Column;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Formatter;
import org.smart.orm.operations.Expression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectExpression extends AbstractExpression {
    
    private final static String EXPRESSION = " SELECT %s ";
    
    private List<Column> columnList = new ArrayList<>();
    
    public SelectExpression(Statement statement) {
        this.statement = statement;
        statement.add(this);
    }
    
    public SelectExpression(Statement statement, TableInfo tableInfo) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = tableInfo;
    }
    
    public SelectExpression(Statement statement, String table) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = new TableInfo(table);
    }
    
    public SelectExpression(Statement context, String table, String alias) {
        this.statement = context;
        context.add(this);
        this.tableInfo = new TableInfo(table, alias);
    }
    
    
    public SelectExpression column(String property) {
        this.columnList.add(new Column(property));
        return this;
    }
    
    public SelectExpression column(String property, String alias) {
        this.columnList.add(new Column(property, alias));
        return this;
    }
    
    public SelectExpression column(Expression expression, String alias) {
        this.columnList.add(new Column(expression, alias));
        return this;
    }
    
    public SelectExpression columns(String... properties) {
        
        for (String property : properties) {
            this.columnList.add(new Column(property));
        }
        
        return this;
    }
    
    public Collection<Column> columns() {
        return columnList;
    }
    
    public FromExpression from() {
        return new FromExpression(statement);
    }
    
    public FromExpression from(String table) {
        return new FromExpression(statement, table);
    }
    
    public FromExpression from(TableInfo table) {
        return new FromExpression(statement, table);
    }
    
    public WhereExpression where(String property, Formatter exp, Object... value) {
        return new WhereExpression(statement, tableInfo, property, exp, value);
    }
    
    public WhereExpression where(String table, String property, Formatter exp, Object... value) {
        return new WhereExpression(statement, table, property, exp, value);
    }
    
    
    public JoinExpression join(JoinExpression operation) {
        operation.setStatement(statement);
        statement.add(operation);
        return operation;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.SELECT;
    }
    
    
    @Override
    public String build() {
        
        String prefix = tableInfo.getName();
        if (StringUtils.isNotEmpty(tableInfo.getAlias())) {
            prefix = tableInfo.getName();
        }
        
        StringBuilder sb = new StringBuilder();
        for (Column column : columnList) {
            
            Expression expression = column.getExpression();
            
            String alias = column.getAlias();
            
            if (expression != null) {
                String text = expression.build();
                if (!StringUtils.isEmpty(alias)) {
                    sb.append(String.format(" (%s) AS `%s` ,", text, alias));
                } else {
                    sb.append(String.format(" (%s) ,", text));
                }
            } else {
                
                String text = column.getName();
                if (!StringUtils.isEmpty(alias)) {
                    sb.append(String.format(" `%s`.`%s` AS `%s` ,", prefix, text, alias));
                } else {
                    sb.append(String.format(" `%s`.`%s` ,", prefix, text));
                }
            }
            
            
        }
        
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        sb.append(" ");
        
        return String.format(EXPRESSION, sb.toString());
        
        
    }
    
    
}

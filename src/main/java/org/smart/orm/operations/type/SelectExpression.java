package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.operations.Column;
import org.smart.orm.operations.AbstractExpression;
import org.smart.orm.operations.Expression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.PropertyGetter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectExpression<T extends Model<T>> extends AbstractExpression {
    
    private final static String EXPRESSION = " SELECT %s ";
    
    private List<Column> columnList = new ArrayList<>();
    
    
    public SelectExpression(Statement statement) {
        this.statement = statement;
        statement.add(this);
        this.tableInfo = T.getMeta(this.getClass()).getTable();
    }
    
    
    public SelectExpression(Statement context, String alias) {
        this.statement = context;
        context.add(this);
        this.tableInfo = T.getMeta(this.getClass()).getTable();
        tableInfo.setAlias(alias);
    }
    
    
    public SelectExpression<T> alias(PropertyGetter<T> property, String alias) {
        Field field = LambdaParser.getGetter(property);
        this.columnList.add(new Column(field.getName(), alias));
        return this;
    }
    
    
    public SelectExpression<T> column(Expression expression, String alias) {
        this.columnList.add(new Column(expression, alias));
        return this;
    }
    
    public SelectExpression<T> column(PropertyGetter<T> property, String alias) {
        Field field = LambdaParser.getGetter(property);
        this.columnList.add(new Column(field.getName(), alias));
        return this;
    }
    
    public SelectExpression<T> columns(PropertyGetter<?>... properties) {
        
        for (PropertyGetter<?> property : properties) {
            Field field = LambdaParser.getGetter(property);
            this.columnList.add(new Column(field.getName()));
        }
        
        return this;
    }
    
    public Collection<Column> columns() {
        return columnList;
    }
    
    public FromExpression<T> from() {
        return new FromExpression<>(statement);
    }
    
    public FromExpression<T> from(String alias) {
        return new FromExpression<>(statement, alias);
    }
    
    
    public WhereExpression<T> where(WhereExpression<T> operation) {
        operation.setTableInfo(this.tableInfo);
        operation.setStatement(statement);
        statement.add(operation);
        return operation;
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
            
            String alias = column.getAlias();
            
            Expression expression = column.getExpression();
            
            
            if (expression != null) {
                String text = expression.build();
                if (StringUtils.isEmpty(alias)) {
                    sb.append(String.format(" (%s) ,", text));
                } else {
                    sb.append(String.format(" (%s) AS `%s` ,", text, alias));
                }
            } else {
                
                String text = column.getName();
                
                if (StringUtils.isEmpty(alias)) {
                    sb.append(String.format(" `%s`.`%s` ,", prefix, text));
                } else {
                    sb.append(String.format(" `%s`.`%s` AS `%s` ,", prefix, text, alias));
                }
            }
            
            
        }
        
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        sb.append(" ");
        
        return String.format(EXPRESSION, sb.toString());
        
        
    }
    
    
}

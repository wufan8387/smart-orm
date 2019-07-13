package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.SelectColumn;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.LambdaParser;
import org.smart.orm.reflect.TableInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SelectOperation<T extends Model<T>> extends AbstractOperation<T> {
    
    private final static String EXPRESSION = " SELECT %s ";
    
    private List<SelectColumn> columnList = new ArrayList<>();
    
    private String expression;
    
    public SelectOperation(UUID batch, OperationContext context) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = T.getMeta(this.getClass()).getTable();
    }
    
    public SelectOperation(UUID batch, OperationContext context, TableInfo tableInfo) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = tableInfo;
    }
    
    public SelectOperation(UUID batch, OperationContext context, String table) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = new TableInfo(table);
    }
    
    public SelectOperation(UUID batch, OperationContext context, String table, String alias) {
        this.batch = batch;
        this.context = context;
        context.add(this);
        this.tableInfo = new TableInfo(table, alias);
    }
    
    
    public SelectOperation<T> alias(String property, String alias) {
        this.columnList.add(new SelectColumn(property, alias));
        return this;
    }
    
    public SelectOperation<T> alias(PropertyGetter<T> property, String alias) {
        Field field = LambdaParser.getGetter(property);
        this.columnList.add(new SelectColumn(field.getName(), alias));
        return this;
    }
    
    public SelectOperation<T> column(String property, String alias) {
        this.columnList.add(new SelectColumn(property, alias));
        return this;
    }
    
    public SelectOperation<T> column(Operation operation, String alias) {
        this.columnList.add(new SelectColumn(operation, alias));
        return this;
    }
    
    public SelectOperation<T> column(PropertyGetter<T> property, String alias) {
        Field field = LambdaParser.getGetter(property);
        this.columnList.add(new SelectColumn(field.getName(), alias));
        return this;
    }
    
    public SelectOperation<T> columns(String... properties) {
        
        for (String property : properties) {
            this.columnList.add(new SelectColumn(property));
        }
        
        return this;
    }
    
    public SelectOperation<T> columns(PropertyGetter<?>... properties) {
        
        for (PropertyGetter<?> property : properties) {
            Field field = LambdaParser.getGetter(property);
            this.columnList.add(new SelectColumn(field.getName()));
        }
        
        return this;
    }
    
    public Collection<SelectColumn> columns() {
        return columnList;
    }
    
    public FromOperation<T> from() {
        return new FromOperation<>(this.batch, context);
    }
    
    public FromOperation<T> from(String table) {
        return new FromOperation<>(this.batch, context, table);
    }
    
    public FromOperation<T> from(TableInfo table) {
        return new FromOperation<>(this.batch, context, table);
    }
    
    public WhereOperation<T> where(WhereOperation<T> operation) {
        operation.setTableInfo(this.tableInfo);
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return operation;
    }
    
    public SelectOperation<T> join(JoinOperation<?, ?> operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return this;
    }
    
    @Override
    public int getPriority() {
        return OperationPriority.SELECT;
    }
    
    
    @Override
    public String getExpression() {
        return expression;
    }
    
    @Override
    public void build() {
        
        String prefix = tableInfo.getName();
        if (StringUtils.isNotEmpty(tableInfo.getAlias())) {
            prefix = tableInfo.getName();
        }
        
        StringBuilder sb = new StringBuilder();
        for (SelectColumn column : columnList) {
            
            String alias = column.getAlias();
            
            Operation operation = column.getOperation();
            
            
            if (operation != null) {
                operation.build();
                String text = operation.getExpression();
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
        
        expression = String.format(EXPRESSION, sb.toString());
        
        
    }
    
    
}

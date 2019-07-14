package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.OperationContext;
import org.smart.orm.data.OperationPriority;
import org.smart.orm.data.SelectColumn;
import org.smart.orm.operations.AbstractOperation;
import org.smart.orm.operations.Operation;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SelectOperation extends AbstractOperation {
    
    private final static String EXPRESSION = " SELECT %s ";
    
    private List<SelectColumn> columnList = new ArrayList<>();
    
    private String expression;
    
    public SelectOperation(UUID batch, OperationContext context) {
        this.batch = batch;
        this.context = context;
        context.add(this);
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
    
    
    public SelectOperation alias(String property, String alias) {
        this.columnList.add(new SelectColumn(property, alias));
        return this;
    }
    
    public SelectOperation column(String property, String alias) {
        this.columnList.add(new SelectColumn(property, alias));
        return this;
    }
    
    public SelectOperation column(Operation operation, String alias) {
        this.columnList.add(new SelectColumn(operation, alias));
        return this;
    }
    
    public SelectOperation columns(String... properties) {
        
        for (String property : properties) {
            this.columnList.add(new SelectColumn(property));
        }
        
        return this;
    }
    
    public Collection<SelectColumn> columns() {
        return columnList;
    }
    
    public FromOperation from() {
        return new FromOperation(this.batch, context);
    }
    
    public FromOperation from(String table) {
        return new FromOperation(this.batch, context, table);
    }
    
    public FromOperation from(TableInfo table) {
        return new FromOperation(this.batch, context, table);
    }
    
    public WhereOperation where(WhereOperation operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        operation.setTableInfo(this.tableInfo);
        context.add(operation);
        return operation;
    }
    
    public JoinOperation join(JoinOperation operation) {
        operation.setContext(context);
        operation.setBatch(batch);
        context.add(operation);
        return operation;
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
            
            Operation operation = column.getOperation();
            
            String alias = column.getAlias();
            
            if (operation != null) {
                operation.build();
                String text = operation.getExpression();
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
        
        expression = String.format(EXPRESSION, sb.toString());
        
        
    }
    
    
}

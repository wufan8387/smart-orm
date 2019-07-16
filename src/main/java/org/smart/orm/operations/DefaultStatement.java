package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.reflect.TableInfo;

import java.util.*;

public class DefaultStatement implements Statement {
    
    private UUID id;
    
    private Map<Integer, List<Expression>> expressionMap = new HashMap<>();
    
    private final List<TableInfo> tableList = new ArrayList<>();
    
    @Override
    public UUID getId() {
        return id;
    }
    
    @Override
    public void add(Expression expression) {
        
        int priority = expression.getPriority();
        
        expressionMap.putIfAbsent(priority, new ArrayList<>());
        
        List<Expression> expressionList = expressionMap.get(priority);
        
        expressionList.add(expression);
        
        
    }
    
    @Override
    public TableInfo getTable(String table) {
        TableInfo tableInfo = new TableInfo(table);
        return getTable(tableInfo);
    }
    
    @Override
    public TableInfo getTable(String table, String alias) {
        TableInfo tableInfo = new TableInfo(table, alias);
        return getTable(tableInfo);
    }
    
    @Override
    public TableInfo getTable(TableInfo tableInfo) {
    
        if (tableList.contains(tableInfo)) {
            int index = tableList.indexOf(tableInfo);
            tableInfo = tableList.get(index);
            if (StringUtils.isNotEmpty(tableInfo.getAlias()))
                tableInfo.setAlias(tableInfo.getAlias());
            return tableInfo;
        } else {
            tableList.add(tableInfo);
            return tableInfo;
        }
    
    
    }
    
    
}

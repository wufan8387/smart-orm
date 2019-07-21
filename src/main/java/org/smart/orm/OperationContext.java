package org.smart.orm;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.execution.Executor;
import org.smart.orm.operations.Expression;
import org.smart.orm.reflect.TableInfo;

import java.util.*;

public class OperationContext {
    
    private final static Map<UUID, List<Expression>> operationMap = new HashMap<>();
    
    
    
    private Executor executor;
    
    public Executor getExecutor() {
        return executor;
    }
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
//    public List<Expression> getOperationList(UUID batch) {
//        return operationMap.get(batch);
//    }
//
//    public void add(Expression expression) {
//        UUID batch = expression.getBatch();
//        operationMap.putIfAbsent(batch, new ArrayList<>());
//
//        List<Expression> expressionList = operationMap.get(batch);
//
//        int priority = expression.getPriority();
//        Optional<Expression> option = expressionList
//                .stream()
//                .filter(t -> t.getPriority() == priority)
//                .findFirst();
//
//        if (option.isPresent()) {
//            option.get().getChildren().add(expression);
//        } else {
//            operationMap.get(batch).add(expression);
//        }
//
//    }
//
    
//    public List<TableInfo> getTable(UUID batch) {
//        return tableMap.get(batch);
//    }
//
//    public TableInfo addTableIfAbsent(UUID batch, String table) {
//        tableMap.putIfAbsent(batch, new ArrayList<>());
//
//        List<TableInfo> tableList = tableMap.get(batch);
//
//        TableInfo tableInfo = new TableInfo(table);
//        if (!tableList.contains(tableInfo)) {
//            tableList.add(tableInfo);
//            return tableInfo;
//        } else {
//            int index = tableList.indexOf(tableInfo);
//            tableInfo = tableList.get(index);
//            return tableInfo;
//        }
//
//
//    }
//
//
//    public TableInfo addTableIfAbsent(UUID batch, String table, String alias) {
//        tableMap.putIfAbsent(batch, new ArrayList<>());
//
//        List<TableInfo> tableList = tableMap.get(batch);
//
//        TableInfo tableInfo = new TableInfo(table, alias);
//        if (!tableList.contains(tableInfo)) {
//            tableList.add(tableInfo);
//            return tableInfo;
//        } else {
//            int index = tableList.indexOf(tableInfo);
//            tableInfo = tableList.get(index);
//
//            if (StringUtils.isNotEmpty(alias))
//                tableInfo.setAlias(alias);
//            return tableInfo;
//        }
//
//
//    }
//
//    public TableInfo addTableIfAbsent(UUID batch, TableInfo tableInfo) {
//        tableMap.putIfAbsent(batch, new ArrayList<>());
//
//        List<TableInfo> tableList = tableMap.get(batch);
//
//        if (!tableList.contains(tableInfo)) {
//            tableList.add(tableInfo);
//            return tableInfo;
//        } else {
//            int index = tableList.indexOf(tableInfo);
//            tableInfo = tableList.get(index);
//            if (StringUtils.isNotEmpty(tableInfo.getAlias()))
//                tableInfo.setAlias(tableInfo.getAlias());
//            return tableInfo;
//        }
//
//
//    }
//
    
    public void query(UUID batchId) {
        ExecuteData data = build(batchId);
//        executor.executeQuery(null, data.sql, data.paramList);
    }
    
    public void update(UUID batchId) {
        ExecuteData data = build(batchId);
//        executor.update(null, data.sql, data.paramList);
    }
    
    public void insert(UUID batchId) {
        ExecuteData data = build(batchId);
//        executor.insert(null, data.sql, data.withKeys, data.paramList);
    }
    
    public void delete(UUID batchId) {
        ExecuteData data = build(batchId);
//        executor.delete(null, data.sql, data.paramList);
    }
    
    
    public ExecuteData build(UUID batchId) {
        
        List<Expression> expressionList = operationMap.get(batchId);
        
        expressionList.sort((o1, o2) -> {
            if (o1.getPriority() < o2.getPriority())
                return -1;
            return 1;
        });
        operationMap.remove(batchId);
        
        StringBuilder sb = new StringBuilder();
        List<Object> paramList = new ArrayList<>();
        for (Expression op : expressionList) {
            op.build();
            sb.append(op.build());
            paramList.addAll(op.getParams());
        }
        ExecuteData data = new ExecuteData();
        data.sql = sb.toString();
        data.paramList = paramList.toArray();
        
        return data;
    }
    
    public static class ExecuteData {
        
        public String sql;
        
        public Object[] paramList;
        
        public boolean withKeys;
        
    }
    
    
}

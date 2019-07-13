package org.smart.orm;

import org.smart.orm.execution.Executor;
import org.smart.orm.operations.Operation;
import org.smart.orm.reflect.TableInfo;

import java.util.*;

public class OperationContext {
    
    private final static Map<UUID, List<Operation>> operationMap = new HashMap<>();
    
    
    private final static Map<UUID, List<TableInfo>> tableMap = new HashMap<>();
    
    
    private Executor executor;
    
    public Executor getExecutor() {
        return executor;
    }
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    public List<Operation> getOperationList(UUID batchId) {
        return operationMap.get(batchId);
    }
    
    public void add(Operation operation) {
        UUID batchId = operation.getBatch();
        operationMap.putIfAbsent(batchId, new ArrayList<>());
        operationMap.get(batchId).add(operation);
    }
    
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
        
        List<Operation> operationList = operationMap.get(batchId);
        
        operationList.sort((o1, o2) -> {
            if (o1.getPriority() < o2.getPriority())
                return -1;
            return 1;
        });
        operationMap.remove(batchId);
        
        StringBuilder sb = new StringBuilder();
        List<Object> paramList = new ArrayList<>();
        for (Operation op : operationList) {
            op.build();
            sb.append(op.getExpression());
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

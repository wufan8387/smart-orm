package org.smart.orm;

import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;
import org.smart.orm.operations.Statement;

import java.util.*;
import java.util.stream.Collectors;

public class OperationContext {
    
    private final static Map<UUID, List<BatchItem>> statementMap = new HashMap<>();
    
    private Executor executor;
    
    private UUID batchId = UUID.randomUUID();
    
    public Executor getExecutor() {
        return executor;
    }
    
    public UUID getBatchId() {
        return batchId;
    }
    
    public void add(Model<?> model, Statement statement) {
        statementMap.putIfAbsent(batchId, new ArrayList<>());
        statementMap.get(batchId).add(new BatchItem(model, statement));
    }
    
    public void add(UUID batchId, Model<?> model, Statement statement) {
        statementMap.putIfAbsent(batchId, new ArrayList<>());
        statementMap.get(batchId).add(new BatchItem(model, statement));
    }
    
    public void include(Statement statement) {
    
    }
    
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    public void saveChanges() {
        List<BatchItem> batchItemList = statementMap.get(batchId)
                .stream()
                .filter(t -> t.statement.getType() == StatementType.DML)
                .collect(Collectors.toList());
        for (BatchItem batchItem : batchItemList) {
            batchItem.statement.execute(executor);
            batchItem.model.getChangeMap().clear();
            
        }
    }
    
    public void saveChanges(UUID batchId) {
        List<BatchItem> batchItemList = statementMap.get(batchId)
                .stream()
                .filter(t -> t.statement.getType() == StatementType.DML && t.statement.getId() == batchId)
                .collect(Collectors.toList());
        for (BatchItem batchItem : batchItemList) {
            batchItem.statement.execute(executor);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public void load() {
//        List<Statement> statementList = statementMap.get(batchId)
////                .stream()
////                .filter(t -> t.getType() == StatementType.DQL)
////                .collect(Collectors.toList());
////        for (Statement statement : statementList) {
////            statement.execute(executor);
////        }
    }
    
    
    private static class BatchItem {
        public Model<?> model;
        
        public Statement statement;
        
        public BatchItem(Model<?> model, Statement statement) {
            this.model = model;
            this.statement = statement;
        }
    }
    
}

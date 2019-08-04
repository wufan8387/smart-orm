package org.smart.orm;

import javafx.stage.StageStyle;
import org.smart.orm.data.NodeType;
import org.smart.orm.data.StatementType;
import org.smart.orm.execution.*;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.type.AttributeNode;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class OperationContext {
    
    private final static Map<UUID, List<Statement>> statementMap = new HashMap<>();
    
    private Executor executor;
    
    private UUID batchId = UUID.randomUUID();
    
    public UUID getBatchId() {
        return batchId;
    }
    
    public void add(Statement statement) {
        statementMap.putIfAbsent(batchId, new ArrayList<>());
        statementMap.get(batchId).add(statement);
    }
    
    public void include(Statement statement) {
    
    }
    
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    public void saveChanges(Connection connection) {
        List<Statement> statementList = statementMap.get(batchId)
                .stream()
                .filter(t -> t.getType() == StatementType.DML)
                .collect(Collectors.toList());
        for (Statement statement : statementList) {
            statement.execute(connection, executor);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public <T> ResultData<T> query(Statement statement, Connection connection) {
        return (ResultData<T>) statement.execute(connection, executor);
    }
    
    
}

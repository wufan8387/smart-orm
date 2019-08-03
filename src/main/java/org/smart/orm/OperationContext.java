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
    
    public void saveChanges(Connection connection) {
//        List<Statement> statementList = statementMap.get(batchId)
//                .stream()
//                .filter(t -> t.getType() == StatementType
//                        .DML)
//                .collect(Collectors.toList());
//        for (Statement statement : statementList) {
//            executor.insert(connection, statement.toString(), statement.getParams().toArray());
//        }
    }
    
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    public <T extends Model<T>> ResultHandler<T> query(Class<T> cls, Connection connection, Statement statement) {
        
        String sql = statement.toString();
        System.out.println(sql);
        
        ObjectHandler<T> handler = new ObjectHandler<>(cls);
        
        List<AttributeNode<?, ?>> attrList = statement.getNodes()
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<?, ?>) t)
                .collect(Collectors.toList());
        
        attrList.forEach(t -> handler.add(t.getAlias(), t.getProp()));
        
        executor.executeQuery(connection, sql, handler, statement.getParams().toArray());
        
        return handler;
    }
    
    
    public void update(Statement statement) {
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Model<T>> void insert(Class<T> cls, Connection connection, Statement statement, T... data) {
        String sql = statement.toString();
        System.out.println(sql);
        
        GeneratedKeysHandler<T> handler = new GeneratedKeysHandler<>(Model.getMeta(cls));
        
        handler.getAll().addAll(Arrays.asList(data));
        
        List<Object> params = statement.getParams();
        
        executor.insert(connection, sql, handler, handler.autoGenerateKeys(), params.toArray());
        
    }
    
    public void delete(Statement statement) {
    
    }
    
    
}

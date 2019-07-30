package org.smart.orm;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultHandler;
import org.smart.orm.operations.Expression;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.TableInfo;

import javax.sql.RowSet;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetWarning;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OperationContext {
    
    private final static Map<UUID, Statement> statementMap = new HashMap<>();
    
    private Executor executor;
    
    
    public Executor getExecutor() {
        return executor;
    }
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    public <T> void query(Connection connection, Executor executor, ResultHandler<T> handler, Statement statement) {
        String sql = statement.toString();
        List<Object> paramList = statement.getParams();
        executor.executeQuery(connection, handler, sql, paramList.toArray());
    }
    
    public CachedRowSet query(Connection connection, Statement statement) {
        String sql = statement.toString();
        List<Object> paramList = statement.getParams();
        return executor.executeQuery(connection, sql, paramList.toArray());
    }
    
    public void update(UUID batchId) {
//        ExecuteData data = build(batchId);
//        executor.update(null, data.sql, data.paramList);
    }
    
    public void insert(UUID batchId) {
//        ExecuteData data = build(batchId);
//        executor.insert(null, data.sql, data.withKeys, data.paramList);
    }
    
    public void delete(UUID batchId) {
//        ExecuteData data = build(batchId);
//        executor.delete(null, data.sql, data.paramList);
    }


//    public ExecuteData build(UUID batchId) {
//
//        List<Expression> expressionList = operationMap.get(batchId);
//
//        expressionList.sort((o1, o2) -> {
//            if (o1.getPriority() < o2.getPriority())
//                return -1;
//            return 1;
//        });
//        operationMap.remove(batchId);
//
//        StringBuilder sb = new StringBuilder();
//        List<Object> paramList = new ArrayList<>();
//        for (Expression op : expressionList) {
//            op.build();
//            sb.append(op.build());
//            paramList.addAll(op.getParams());
//        }
//        ExecuteData data = new ExecuteData();
//        data.sql = sb.toString();
//        data.paramList = paramList.toArray();
//
//        return data;
//    }
    
    public static class ExecuteData {
        
        public String sql;
        
        public Object[] paramList;
        
        public boolean withKeys;
        
    }
    
    
}

package org.smart.orm.operations;

import org.smart.orm.OperationContext;
import org.smart.orm.reflect.TableInfo;

import java.util.List;
import java.util.UUID;

public interface Expression {
    
    UUID getId();
    
    int getPriority();
    
    String build();
    
    void build(StringBuilder sb);
    
    List<Object> getParams();
    
    List<Expression> getChildren();
    
    Statement getStatement();
    
    void setStatement(Statement statement);
    
    TableInfo getTableInfo();
    
    void setTableInfo(TableInfo tableInfo);
    
    
    
    
}

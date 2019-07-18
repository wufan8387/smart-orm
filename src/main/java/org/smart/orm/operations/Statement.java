package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.reflect.TableInfo;

import java.util.*;

public interface Statement {
    
    UUID getId();
    
    void add(Expression expression);
    
    void add(SqlNode node);
    
    
    TableInfo getTable(String table);
    
    TableInfo getTable(String table, String alias);
    
    TableInfo getTable(TableInfo tableInfo);
    
    
}

package org.smart.orm.operations.text;

import org.smart.orm.operations.Expression;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.reflect.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QueryStatement implements Statement {
    
    
    private final List<SqlNode> relattrlist = new ArrayList<>();
    
    private final List<SqlNode> rellist = new ArrayList<>();
    
    private final List<SqlNode> conditionlist = new ArrayList<>();
    
    private final List<SqlNode> orderrelattr = new ArrayList<>();
    
    private final List<SqlNode> grouprelattr = new ArrayList<>();
    
    
    @Override
    public UUID getId() {
        return null;
    }
    
    @Override
    public void add(Expression expression) {
    
    }
    
    @Override
    public TableInfo getTable(String table) {
        return null;
    }
    
    @Override
    public TableInfo getTable(String table, String alias) {
        return null;
    }
    
    @Override
    public TableInfo getTable(TableInfo tableInfo) {
        return null;
    }
    
    public FromNode from(String table) {
        FromNode node = new FromNode(this, table);
        return node;
    }
    
}
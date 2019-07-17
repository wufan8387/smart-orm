package org.smart.orm.operations.text;

import org.smart.orm.operations.SqlNode;

import java.util.ArrayList;
import java.util.List;

public class RelationNode implements SqlNode {
    
    private String name;
    
    private String alias;
    
    private List<SqlNode> conditionList = new ArrayList<>();
    
    @Override
    public int getType() {
        return 0;
    }
    
    @Override
    public void add(SqlNode node) {
    
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
}

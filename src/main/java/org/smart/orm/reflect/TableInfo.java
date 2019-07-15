package org.smart.orm.reflect;

import org.smart.orm.annotations.Table;

public class TableInfo {
    
    private String alias;
    
    private String name;
    
    public TableInfo() {
    }
    
    public TableInfo(String table) {
        this.name = table;
    }
    
    public TableInfo(String table, String alias) {
        this.name = table;
        this.alias = alias;
    }
    
    public TableInfo(Table table) {
        this.name = table.value();
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
    
    public TableInfo copy() {
        return new TableInfo(name, alias);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null)
            return false;
        
        if (!(obj instanceof TableInfo))
            return false;
        
        TableInfo tableInfo = (TableInfo) obj;
        
        return this.name.equals(tableInfo.name);
        
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

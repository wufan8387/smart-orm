package org.smart.orm.reflect;

import org.smart.orm.annotations.Table;

public class TableInfo {
    
    public String name;
    
    
    public TableInfo() {
    }
    
    public TableInfo(String table) {
        this.name = table;
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
}

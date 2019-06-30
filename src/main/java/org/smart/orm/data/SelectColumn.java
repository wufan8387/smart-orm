package org.smart.orm.data;

import org.smart.orm.Operation;

public final class SelectColumn {
    
    
    public String property;
    
    public String alias;
    
    public Operation operation;
    
    public SelectColumn() {
    }
    
    public SelectColumn(String property) {
        this.property = property;
    }
    
    public SelectColumn(String property, String alias) {
        this.property = property;
        this.alias = alias;
    }
    
    
    public SelectColumn(Operation operation) {
        this.operation = operation;
    }
    
    public SelectColumn(Operation operation, String alias) {
        this.operation = operation;
        this.alias = alias;
    }
    
    
}

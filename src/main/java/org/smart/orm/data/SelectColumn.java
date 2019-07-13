package org.smart.orm.data;

import org.smart.orm.operations.Operation;

public final class SelectColumn {
    
    
    private String name;
    
    private String alias;
    
    private Operation operation;
    
    
    public SelectColumn(String name) {
        this.name = name;
    }
    
    public SelectColumn(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
    
    
    public SelectColumn(Operation operation) {
        this.operation = operation;
    }
    
    public SelectColumn(Operation operation, String alias) {
        this.operation = operation;
        this.alias = alias;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public Operation getOperation() {
        return operation;
    }
}

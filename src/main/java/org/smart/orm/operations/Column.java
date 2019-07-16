package org.smart.orm.operations;

import org.smart.orm.operations.Expression;

public final class Column {
    
    
    private String name;
    
    private String alias;
    
    private Expression expression;
    
    
    public Column(String name) {
        this.name = name;
    }
    
    public Column(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
    
    
    public Column(Expression expression) {
        this.expression = expression;
    }
    
    public Column(Expression expression, String alias) {
        this.expression = expression;
        this.alias = alias;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public Expression getExpression() {
        return expression;
    }
}

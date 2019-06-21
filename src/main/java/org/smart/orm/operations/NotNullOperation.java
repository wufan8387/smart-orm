package org.smart.orm.operations;

import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class NotNullOperation<T> extends WhereOperation<T> {
    
    
    public NotNullOperation() {
    }
    
    public NotNullOperation(Getter<T> property) {
        super(WhereType.NONE, property);
    }
    
    public NotNullOperation(String property) {
        super(WhereType.NONE, property);
    }
    
    public NotNullOperation(WhereType whereType, Getter<T> property) {
        super(whereType, property);
    }
    
    public NotNullOperation(WhereType whereType, String property) {
        super(whereType, property);
    }
    
    private final static String EXPRESSION = " %s %s is not null ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
    }
    
}

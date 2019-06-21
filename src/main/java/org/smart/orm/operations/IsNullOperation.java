package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class IsNullOperation<T> extends WhereOperation<T> {
    
    public IsNullOperation(){
    
    }
    
    public IsNullOperation(Getter<T> property) {
        super(WhereType.NONE, property);
    }
    
    public IsNullOperation(String property) {
        super(WhereType.NONE, property);
    }
    
    public IsNullOperation(WhereType whereType, Getter<T> property) {
        super(whereType, property);
    }
    
    public IsNullOperation(WhereType whereType, String property) {
        super(whereType, property);
    }
    
    private final static String EXPRESSION = " %s %s is null ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
    }
    
    
}

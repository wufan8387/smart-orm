package org.smart.orm.operations;

import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

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
    protected void build(TableInfo tableInfo, PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn());
        this.params.clear();
    }
    
}

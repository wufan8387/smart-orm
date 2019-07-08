package org.smart.orm.operations;

import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

public class GreatThanOperation<T> extends WhereOperation<T> {
    
    
    private Object value;
    
    public GreatThanOperation() {
    }
    
    
    public GreatThanOperation(  Getter<T> property, Object value) {
        super( WhereType.NONE, property);
        this.value = value;
    }
    
    public GreatThanOperation(  String property, Object value) {
        super( WhereType.NONE, property);
        this.value = value;
    }
    
    public GreatThanOperation(  WhereType whereType, Getter<T> property, Object value) {
        super( whereType, property);
        this.value = value;
    }
    
    public GreatThanOperation(  WhereType whereType, String property, Object value) {
        super( whereType, property);
        this.value = value;
    }
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    private final static String EXPRESSION = " %s %s > ? ";
    
    
    @Override
    protected void build(TableInfo tableInfo, PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn());
        this.params.clear();
        this.params.add(value);
    }
    
    
}

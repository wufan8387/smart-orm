package org.smart.orm.operations;

import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class LessThanOperation<T> extends WhereOperation<T> {
    
    private Object value;
    
    public LessThanOperation() {
    }
    
    
    public LessThanOperation(Getter<T> property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public LessThanOperation(String property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public LessThanOperation(WhereType whereType, Getter<T> property, Object value) {
        super(whereType, property);
    }
    
    public LessThanOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
    }
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    
    private final static String EXPRESSION = " %s %s < ( ? ) ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn());
        this.params.clear();
        this.params.add(value);
    }
    
    
}

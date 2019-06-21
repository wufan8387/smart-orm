package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class GreatThanEqualOperation<T> extends WhereOperation<T> {
    
    private Object value;
    
    public GreatThanEqualOperation() {
    
    }
    
    public GreatThanEqualOperation(Getter<T> property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public GreatThanEqualOperation(String property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public GreatThanEqualOperation(WhereType whereType, Getter<T> property, Object value) {
        super(whereType, property);
    }
    
    public GreatThanEqualOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
    }
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    private final static String EXPRESSION = " %s %s >= ? ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
        this.params.add(value);
    }
    
}

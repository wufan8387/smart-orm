package org.smart.orm.operations;

import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

public class EqualOperation<T> extends WhereOperation<T> {
    
    private final static String EXPRESSION = " %s %s = ? ";
    
    private Object value;
    
    public EqualOperation() {
    }
    
    
    public EqualOperation(Getter<T> property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public EqualOperation(String property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, Getter<T> property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    @Override
    protected void build(TableInfo tableInfo, PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn());
        this.params.clear();
        this.params.add(value);
    }
    
    
}

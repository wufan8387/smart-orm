package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class BetweenOperation<T> extends WhereOperation<T> {
    
    private final static String EXPRESSION = " %s %s between ? and ? ";
    
    private Object value1;
    
    private Object value2;
    
    public BetweenOperation() {
    }
    
    
    
    public BetweenOperation(  Getter<T> property, Object value1, Object value2) {
        super( WhereType.NONE, property);
        this.value1 = value1;
        this.value2 = value2;
    }
    
    public BetweenOperation(  String property, Object value1, Object value2) {
        super( WhereType.NONE, property);
        this.value1 = value1;
        this.value2 = value2;
        
    }
    
    public BetweenOperation(  WhereType whereType, Getter<T> property, Object value1, Object value2) {
        super( whereType, property);
        this.value1 = value1;
        this.value2 = value2;
        
    }
    
    public BetweenOperation(  WhereType whereType, String property, Object value1, Object value2) {
        super( whereType, property);
        this.value1 = value1;
        this.value2 = value2;
    }
    
    public Object getValue1() {
        return value1;
    }
    
    public void setValue1(Object value1) {
        this.value1 = value1;
    }
    
    public Object getValue2() {
        return value2;
    }
    
    public void setValue2(Object value2) {
        this.value2 = value2;
    }
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
        this.params.add(value1);
        this.params.add(value2);
    }
    
    
}

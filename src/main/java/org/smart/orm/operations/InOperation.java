package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class InOperation<T> extends WhereOperation<T> {
    
    private Object[] values;
    
    public InOperation() {
    }
    
    
    public InOperation(Getter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
        
    }
    
    public InOperation(String property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
        
    }
    
    public InOperation(WhereType whereType, Getter<T> property, Object... values) {
        super(whereType, property);
        this.values = values;
        
    }
    
    public InOperation(WhereType whereType, String property, Object... values) {
        super(whereType, property);
        this.values = values;
        
    }
    
    private final static String EXPRESSION = " %s %s in ( ? ) ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
        for (Object value : values) {
            this.params.add(value);
        }
        
    }
    
    
}

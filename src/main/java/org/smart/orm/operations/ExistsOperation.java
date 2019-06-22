package org.smart.orm.operations;

import org.smart.orm.Operation;
import org.smart.orm.OperationContext;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class ExistsOperation<T> extends WhereOperation<T> {
    
    private Object[] values;
    
    public ExistsOperation() {
    }
    
    
    public ExistsOperation(  Getter<T> property, Object... values) {
        super( WhereType.NONE, property);
        this.values = values;
    }
    
    public ExistsOperation(  String property, Object... values) {
        super(WhereType.NONE, property);
    }
    
    public ExistsOperation(  WhereType whereType, Getter<T> property, Object... values) {
        super( whereType, property);
    }
    
    public ExistsOperation(  WhereType whereType, String property, Object... values) {
        super( whereType, property);
    }
    
    private final static String EXPRESSION = " %s %s exists ( ? ) ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
        for (Object value : values) {
            this.params.add(value);
        }
        
    }
    
    
}

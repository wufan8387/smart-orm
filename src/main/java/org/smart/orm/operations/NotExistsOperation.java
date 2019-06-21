package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class NotExistsOperation<T> extends WhereOperation<T> {
    
    private Object[] values;
    
    public NotExistsOperation(){
    
    }
    
    public NotExistsOperation(Getter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    
    }
    
    public NotExistsOperation(String property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    
    }
    
    public NotExistsOperation(WhereType whereType, Getter<T> property, Object... values) {
        super(whereType, property);
        this.values = values;
    
    }
    
    public NotExistsOperation(WhereType whereType, String property, Object... values) {
        super(whereType, property);
        this.values = values;
    
    }
    
    public Object[] getValues() {
        return values;
    }
    
    public void setValues(Object[] values) {
        this.values = values;
    }
    
    
    private final static String EXPRESSION = " %s %s not exists ( ? ) ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
        for (Object value : values) {
            this.params.add(value);
        }
    }
    
    
}

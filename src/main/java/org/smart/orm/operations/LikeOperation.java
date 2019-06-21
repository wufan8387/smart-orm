package org.smart.orm.operations;

import org.smart.orm.reflect.Getter;
import org.smart.orm.reflect.PropertyInfo;

public class LikeOperation<T> extends WhereOperation<T> {
    
    private Object value;
    
    public LikeOperation(Getter<T> property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public LikeOperation(String property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public LikeOperation(WhereType whereType, Getter<T> property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public LikeOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    private final static String EXPRESSION = " %s %s like ? ";
    
    
    @Override
    protected void build(PropertyInfo propertyInfo) {
        this.expression = String.format(EXPRESSION, whereText(), propertyInfo.getColumn().name());
        this.params.clear();
        this.params.add(value);
    }
    
    
}

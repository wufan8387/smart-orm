package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

public class LessThanEqualOperation<T extends Model<T>> extends WhereOperation<T> {
    
    private Object value;
    
    public LessThanEqualOperation() {
    }
    
    
    public LessThanEqualOperation(PropertyGetter<T> property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public LessThanEqualOperation(String property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public LessThanEqualOperation(WhereType whereType, PropertyGetter<T> property, Object value) {
        super(whereType, property);
    }
    
    public LessThanEqualOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
    }
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    private final static String EXPRESSION = " %s %s <= ( ? ) ";
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), property);
        this.params.clear();
        this.params.add(value);
    }
    
}

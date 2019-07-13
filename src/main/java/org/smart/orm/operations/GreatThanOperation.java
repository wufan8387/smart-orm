package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

public class GreatThanOperation<T extends Model<T>> extends WhereOperation<T> {
    
    
    private Object value;
    
    public GreatThanOperation() {
    }
    
    
    public GreatThanOperation(PropertyGetter<T> property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public GreatThanOperation(String property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public GreatThanOperation(WhereType whereType, PropertyGetter<T> property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public GreatThanOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
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
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), property);
        this.params.clear();
        this.params.add(value);
    }
    
    
}

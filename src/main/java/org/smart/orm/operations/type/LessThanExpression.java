package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class LessThanExpression<T extends Model<T>> extends WhereExpression<T> {
    
    private Object value;
    
    public LessThanExpression() {
    }
    
    
    public LessThanExpression(PropertyGetter<T> property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public LessThanExpression(WhereType whereType, PropertyGetter<T> property, Object value) {
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
    protected String build(TableInfo tableInfo, String property) {
        this.params.clear();
        this.params.add(value);
        return String.format(EXPRESSION, whereText(), property);
        
    }
    
    
}

package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class GreatThanEqualExpression<T extends Model<T>> extends WhereExpression<T> {
    
    private Object value;
    
    public GreatThanEqualExpression() {
    }
    
    
    public GreatThanEqualExpression(PropertyGetter<T> property, Object value) {
        super(WhereType.NONE, property);
    }
    
    public GreatThanEqualExpression(WhereType whereType, PropertyGetter<T> property, Object value) {
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
    protected String build(TableInfo tableInfo, String property) {
        this.params.clear();
        this.params.add(value);
        return String.format(EXPRESSION, whereText(), property);
        
    }
    
}

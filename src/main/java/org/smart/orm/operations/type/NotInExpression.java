package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

import java.util.Collections;

public class NotInExpression<T extends Model<T>> extends WhereExpression<T> {
    
    private Object[] values;
    
    public NotInExpression() {
    }
    
    
    public NotInExpression(PropertyGetter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    }
    
    public NotInExpression(WhereType whereType, PropertyGetter<T> property, Object... values) {
        super(whereType, property);
        this.values = values;
    }
    
    private final static String EXPRESSION = " %s %s not in ( ? ) ";
    
    
    @Override
    protected String build(TableInfo tableInfo, String property) {
        this.params.clear();
        Collections.addAll(this.params, values);
        return String.format(EXPRESSION, whereText(), property);
        
    }
    
    
}

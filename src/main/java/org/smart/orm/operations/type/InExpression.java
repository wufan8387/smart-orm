package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class InExpression<T extends Model<T>> extends WhereExpression<T> {
    
    private Object[] values;
    
    public InExpression() {
    }
    
    
    public InExpression(PropertyGetter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
        
    }
    
    
    public InExpression(WhereType whereType, PropertyGetter<T> property, Object... values) {
        super(whereType, property);
        this.values = values;
        
    }
    
    
    private final static String EXPRESSION = " %s %s in ( ? ) ";
    
    
    @Override
    protected String build(TableInfo tableInfo, String property) {
        this.params.clear();
        for (Object value : values) {
            this.params.add(value);
        }
        return String.format(EXPRESSION, whereText(), property);
    
    }
    
    
}

package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

import java.util.Arrays;

public class ExistsExpression<T extends Model<T>> extends WhereExpression<T> {
    
    private Object[] values;
    
    public ExistsExpression() {
    }
    
    
    public ExistsExpression(PropertyGetter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    }
    
    public ExistsExpression(WhereType whereType, PropertyGetter<T> property, Object... values) {
        super(whereType, property);
    }
    
    private final static String EXPRESSION = " %s %s exists ( ? ) ";
    
    
    @Override
    protected String build(TableInfo tableInfo, String property) {
        this.params.clear();
        this.params.addAll(Arrays.asList(values));
        return String.format(EXPRESSION, whereText(), property);
        
    }
    
    
}

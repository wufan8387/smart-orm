package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

import java.util.Collections;

public class NotExistsOperation<T extends Model<T>> extends WhereOperation<T> {
    
    private Object[] values;
    
    public NotExistsOperation() {
    }
    
    
    public NotExistsOperation(PropertyGetter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
        
    }
    
    public NotExistsOperation(String property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
        
    }
    
    public NotExistsOperation(WhereType whereType, PropertyGetter<T> property, Object... values) {
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
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), property);
        this.params.clear();
        Collections.addAll(this.params, values);
    }
    
    
}

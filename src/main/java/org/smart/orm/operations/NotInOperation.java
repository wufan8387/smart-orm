package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

import java.util.Collections;

public class NotInOperation<T extends Model<T>> extends WhereOperation<T> {
    
    private Object[] values;
    
    public NotInOperation() {
    }
    
    
    public NotInOperation(PropertyGetter<T> property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    }
    
    public NotInOperation(String property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    }
    
    public NotInOperation(WhereType whereType, PropertyGetter<T> property, Object... values) {
        super(whereType, property);
        this.values = values;
    }
    
    public NotInOperation(WhereType whereType, String property, Object... values) {
        super(whereType, property);
        this.values = values;
    }
    
    private final static String EXPRESSION = " %s %s not in ( ? ) ";
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), property);
        this.params.clear();
        Collections.addAll(this.params, values);
    }
    
    
}

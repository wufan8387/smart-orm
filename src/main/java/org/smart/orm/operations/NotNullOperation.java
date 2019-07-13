package org.smart.orm.operations;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

public class NotNullOperation<T extends Model<T>> extends WhereOperation<T> {
    
    public NotNullOperation() {
    }
    
    
    public NotNullOperation(PropertyGetter<T> property) {
        super(WhereType.NONE, property);
    }
    
    public NotNullOperation(String property) {
        super(WhereType.NONE, property);
    }
    
    public NotNullOperation(WhereType whereType, PropertyGetter<T> property) {
        super(whereType, property);
    }
    
    public NotNullOperation(WhereType whereType, String property) {
        super(whereType, property);
    }
    
    private final static String EXPRESSION = " %s %s is not null ";
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), property);
        this.params.clear();
    }
    
}

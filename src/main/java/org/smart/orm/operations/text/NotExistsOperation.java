package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

import java.util.Collections;

public class NotExistsOperation extends WhereOperation {
    
    private final static String EXPRESSION = " %s `%s`.`%s` NOT EXISTS ( ? ) ";
    
    private Object[] values;
    
    public NotExistsOperation() {
    }
    
    public NotExistsOperation(String property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    }
    
    public NotExistsOperation(String table, String property, Object... values) {
        super(WhereType.NONE, table, property);
        this.values = values;
    }
    
    public NotExistsOperation(TableInfo table, String property, Object... values) {
        super(WhereType.NONE, table, property);
        this.values = values;
    }
    
    public NotExistsOperation(WhereType whereType, String property, Object... values) {
        super(whereType, property);
        this.values = values;
    }
    
    public NotExistsOperation(WhereType whereType, String table, String property, Object... values) {
        super(whereType, table, property);
        this.values = values;
    }
    
    public NotExistsOperation(WhereType whereType, TableInfo table, String property, Object... values) {
        super(whereType, table, property);
        this.values = values;
    }
    
    public Object[] getValues() {
        return values;
    }
    
    public void setValues(Object[] values) {
        this.values = values;
    }
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), tableInfo.getAlias(), property);
        this.params.clear();
        Collections.addAll(this.params, values);
    }
    
    
}

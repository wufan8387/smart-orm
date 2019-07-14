package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

import java.util.Arrays;

public class ExistsOperation extends WhereOperation {
    
    private final static String EXPRESSION = " %s `%s`.`%s` exists ( ? ) ";
    
    private Object[] values;
    
    public ExistsOperation() {
    }
    
    
    public ExistsOperation(String property, Object... values) {
        super(WhereType.NONE, property);
        this.values = values;
    }
    
    public ExistsOperation(String table, String property, Object... values) {
        super(WhereType.NONE, table, property);
        this.values = values;
    }
    
    public ExistsOperation(TableInfo table, String property, Object... values) {
        super(WhereType.NONE, table, property);
        this.values = values;
    }
    
    public ExistsOperation(WhereType whereType, String property, Object... values) {
        super(whereType, property);
        this.values = values;
    }
    
    public ExistsOperation(WhereType whereType, String table, String property, Object... values) {
        super(whereType, table, property);
        this.values = values;
    }
    
    
    public ExistsOperation(WhereType whereType, TableInfo table, String property, Object... values) {
        super(whereType, table, property);
        this.values = values;
    }
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), tableInfo.getAlias(), property);
        this.params.clear();
        this.params.addAll(Arrays.asList(values));
        
    }
    
    
}

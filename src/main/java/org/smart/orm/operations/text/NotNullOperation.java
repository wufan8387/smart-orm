package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.operations.text.WhereOperation;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class NotNullOperation extends WhereOperation {
    
    private final static String EXPRESSION = " %s `%s`.`%s` IS NOT nULL ";
    
    public NotNullOperation() {
    }
    
    public NotNullOperation(String property) {
        super(WhereType.NONE, property);
    }
    
    public NotNullOperation(String table, String property) {
        super(WhereType.NONE, table, property);
    }
    
    public NotNullOperation(TableInfo table, String property) {
        super(WhereType.NONE, table, property);
    }
    
    public NotNullOperation(WhereType whereType, String property) {
        super(whereType, property);
    }
    
    public NotNullOperation(WhereType whereType, String table, String property) {
        super(whereType, table, property);
    }
    
    public NotNullOperation(WhereType whereType, TableInfo table, String property) {
        super(whereType, table, property);
    }
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), tableInfo.getAlias(), property);
        this.params.clear();
    }
    
}

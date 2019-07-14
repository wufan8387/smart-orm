package org.smart.orm.operations.text;


import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class IsNullOperation extends WhereOperation {
    
    private final static String EXPRESSION = " %s `%s`.`%s` IS NULL ";
    
    public IsNullOperation() {
    }
    
    public IsNullOperation(String property) {
        super(WhereType.NONE, property);
    }
    
    public IsNullOperation(String table, String property) {
        super(WhereType.NONE, table, property);
    }
    
    public IsNullOperation(TableInfo table, String property) {
        super(WhereType.NONE, table, property);
    }
    
    public IsNullOperation(WhereType whereType, String property) {
        super(whereType, property);
    }
    
    public IsNullOperation(WhereType whereType, String table, String property) {
        super(whereType, table, property);
    }
    
    public IsNullOperation(WhereType whereType, TableInfo table, String property) {
        super(whereType, table, property);
    }
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), tableInfo.getAlias(), property);
        this.params.clear();
    }
    
    
}

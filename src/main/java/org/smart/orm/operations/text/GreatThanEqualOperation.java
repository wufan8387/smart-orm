package org.smart.orm.operations.text;

import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.TableInfo;

public class GreatThanEqualOperation extends WhereOperation {
    
    private final static String EXPRESSION = " %s `%s`.`%s` >= ? ";
    
    private Object value;
    
    public GreatThanEqualOperation() {
    }
    
    
    public GreatThanEqualOperation(String property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public GreatThanEqualOperation(String table, String property, Object value) {
        super(WhereType.NONE, table, property);
        this.value = value;
    }
    
    public GreatThanEqualOperation(TableInfo table, String property, Object value) {
        super(WhereType.NONE, table, property);
        this.value = value;
    }
    
    
    public GreatThanEqualOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public GreatThanEqualOperation(WhereType whereType, String table, String property, Object value) {
        super(whereType, table, property);
        this.value = value;
    }
    
    public GreatThanEqualOperation(WhereType whereType, TableInfo table, String property, Object value) {
        super(whereType, table, property);
        this.value = value;
    }
    
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        this.expression = String.format(EXPRESSION, whereText(), tableInfo.getAlias(), property);
        this.params.clear();
        this.params.add(value);
    }
    
}

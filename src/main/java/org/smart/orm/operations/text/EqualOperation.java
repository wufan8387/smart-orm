package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class EqualOperation extends WhereOperation {
    
    private final static String EXPRESSION = " %s `%s`.`%s` = ? ";
    
    private Object value;
    
    public EqualOperation() {
    }
    
    public EqualOperation(String property, Object value) {
        super(WhereType.WHERE, property);
        this.value = value;
    }
    
    public EqualOperation(String table, String property, Object value) {
        super(WhereType.WHERE, table, property);
        this.value = value;
    }
    
    public EqualOperation(TableInfo table, String property, Object value) {
        super(WhereType.WHERE, table, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, String table, String property, Object value) {
        super(whereType, table, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, TableInfo table, String property, Object value) {
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

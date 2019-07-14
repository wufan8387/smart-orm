package org.smart.orm.operations.text;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;
import org.springframework.util.StringUtils;

public class LikeOperation extends WhereOperation {
    
    private Object value;
    
    public LikeOperation() {
    }
    
    public LikeOperation(String property, String value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public LikeOperation(String table, String property, String value) {
        super(WhereType.NONE, table, property);
        this.value = value;
    }
    
    public LikeOperation(TableInfo table, String property, String value) {
        super(WhereType.NONE, table, property);
        this.value = value;
    }
    
    
    public LikeOperation(WhereType whereType, String property, String value) {
        super(whereType, property);
        this.value = value;
    }
    
    public LikeOperation(WhereType whereType, String table, String property, String value) {
        super(whereType, table, property);
        this.value = value;
    }
    
    public LikeOperation(WhereType whereType, TableInfo table, String property, String value) {
        super(whereType, table, property);
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    private final static String EXPRESSION = " %s `%s`.`%s` LIKE ? ";
    
    
    @Override
    protected void build(TableInfo tableInfo, String property) {
        
        
        this.expression = String.format(EXPRESSION, whereText(), tableInfo.getAlias(), property);
        
        
        this.params.clear();
        this.params.add(value);
    }
    
    
}

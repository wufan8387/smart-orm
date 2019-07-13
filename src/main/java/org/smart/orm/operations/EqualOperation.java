package org.smart.orm.operations;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.PropertyInfo;
import org.smart.orm.reflect.TableInfo;

public class EqualOperation<T extends Model<T>> extends WhereOperation<T> {
    
    private final static String EXPRESSION = " %s `%s`.`%s` = ? ";
    
    private Object value;
    
    public EqualOperation() {
    }
    
    
    public EqualOperation(PropertyGetter<T> property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public EqualOperation(String property, Object value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, PropertyGetter<T> property, Object value) {
        super(whereType, property);
        this.value = value;
    }
    
    public EqualOperation(WhereType whereType, String property, Object value) {
        super(whereType, property);
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
        String alias = tableInfo.getAlias();
        if (StringUtils.isEmpty(alias)) {
            this.expression = String.format(EXPRESSION, whereText(), tableInfo.getName(), property);
        } else {
            this.expression = String.format(EXPRESSION, whereText(), alias, property);
        }
        this.params.clear();
        this.params.add(value);
    }
    
    
}

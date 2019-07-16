package org.smart.orm.operations.type;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;

public class EqualExpression<T extends Model<T>> extends WhereExpression<T> {
    
    private final static String EXPRESSION = " %s `%s`.`%s` = ? ";
    
    private Object value;
    
    public EqualExpression() {
    }
    
    
    public EqualExpression(PropertyGetter<T> property, Object value) {
        super(WhereType.WHERE, property);
        this.value = value;
    }
    
    
    public EqualExpression(WhereType whereType, PropertyGetter<T> property, Object value) {
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
    protected String build(TableInfo tableInfo, String property) {
        
        this.params.clear();
        this.params.add(value);
        String alias = tableInfo.getAlias();
        if (StringUtils.isEmpty(alias)) {
            return String.format(EXPRESSION, whereText(), tableInfo.getName(), property);
        } else {
            return String.format(EXPRESSION, whereText(), alias, property);
        }
        
    }
    
    
}

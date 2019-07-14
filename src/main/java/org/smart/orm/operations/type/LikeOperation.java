package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.WhereType;
import org.smart.orm.reflect.PropertyGetter;
import org.smart.orm.reflect.TableInfo;
import org.springframework.util.StringUtils;

public class LikeOperation<T extends Model<T>> extends WhereOperation<T> {
    
    private Object value;
    
    public LikeOperation() {
    }
    
    
    public LikeOperation(PropertyGetter<T> property, String value) {
        super(WhereType.NONE, property);
        this.value = value;
    }
    
    
    public LikeOperation(WhereType whereType, PropertyGetter<T> property, String value) {
        super(whereType, property);
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    private final static String EXPRESSION = " %s `%s`.`%s` like ? ";
    
    
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

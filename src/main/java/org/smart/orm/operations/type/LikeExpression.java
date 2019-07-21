//package org.smart.orm.operations.type;
//
//import org.smart.orm.Model;
//import org.smart.orm.data.WhereType;
//import org.smart.orm.reflect.PropertyGetter;
//import org.smart.orm.reflect.TableInfo;
//import org.springframework.util.StringUtils;
//
//public class LikeExpression<T extends Model<T>> extends WhereExpression<T> {
//
//    private Object value;
//
//    public LikeExpression() {
//    }
//
//
//    public LikeExpression(PropertyGetter<T> property, String value) {
//        super(WhereType.NONE, property);
//        this.value = value;
//    }
//
//
//    public LikeExpression(WhereType whereType, PropertyGetter<T> property, String value) {
//        super(whereType, property);
//        this.value = value;
//    }
//
//    public Object getValue() {
//        return value;
//    }
//
//    public void setValue(Object value) {
//        this.value = value;
//    }
//
//    private final static String EXPRESSION = " %s `%s`.`%s` like ? ";
//
//
//    @Override
//    protected String build(TableInfo tableInfo, String property) {
//
//        this.params.clear();
//        this.params.add(value);
//        String alias = tableInfo.getAlias();
//        if (StringUtils.isEmpty(alias)) {
//            return String.op(EXPRESSION, whereText(), tableInfo.getName(), property);
//        } else {
//            return String.op(EXPRESSION, whereText(), alias, property);
//        }
//    }
//
//
//}

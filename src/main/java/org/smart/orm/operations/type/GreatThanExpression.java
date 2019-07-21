//package org.smart.orm.operations.type;
//
//import org.smart.orm.Model;
//import org.smart.orm.data.WhereType;
//import org.smart.orm.reflect.PropertyGetter;
//import org.smart.orm.reflect.TableInfo;
//
//public class GreatThanExpression<T extends Model<T>> extends WhereExpression<T> {
//
//
//    private Object value;
//
//    public GreatThanExpression() {
//    }
//
//
//    public GreatThanExpression(PropertyGetter<T> property, Object value) {
//        super(WhereType.NONE, property);
//        this.value = value;
//    }
//
//    public GreatThanExpression(WhereType whereType, PropertyGetter<T> property, Object value) {
//        super(whereType, property);
//        this.value = value;
//    }
//
//
//    public Object getValue() {
//        return value;
//    }
//
//    public void setValue(Object value) {
//        this.value = value;
//    }
//
//    private final static String EXPRESSION = " %s %s > ? ";
//
//
//    @Override
//    protected String build(TableInfo tableInfo, String property) {
//        this.params.clear();
//        this.params.add(value);
//        return String.op(EXPRESSION, whereText(), property);
//
//    }
//
//
//}

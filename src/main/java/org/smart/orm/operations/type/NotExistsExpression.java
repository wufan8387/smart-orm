//package org.smart.orm.operations.type;
//
//import org.smart.orm.Model;
//import org.smart.orm.data.WhereType;
//import org.smart.orm.reflect.PropertyGetter;
//import org.smart.orm.reflect.TableInfo;
//
//import java.util.Collections;
//
//public class NotExistsExpression<T extends Model<T>> extends WhereExpression<T> {
//
//    private Object[] values;
//
//    public NotExistsExpression() {
//    }
//
//
//    public NotExistsExpression(PropertyGetter<T> property, Object... values) {
//        super(WhereType.NONE, property);
//        this.values = values;
//
//    }
//
//    public NotExistsExpression(WhereType whereType, PropertyGetter<T> property, Object... values) {
//        super(whereType, property);
//        this.values = values;
//
//    }
//
//    public Object[] getValues() {
//        return values;
//    }
//
//    public void setValues(Object[] values) {
//        this.values = values;
//    }
//
//
//    private final static String EXPRESSION = " %s %s not exists ( ? ) ";
//
//
//    @Override
//    protected String build(TableInfo tableInfo, String property) {
//        this.params.clear();
//        Collections.addAll(this.params, values);
//        return String.op(EXPRESSION, whereText(), property);
//
//    }
//
//
//}

//package org.smart.orm.operations.type;
//
//import org.smart.orm.Model;
//import org.smart.orm.data.WhereType;
//import org.smart.orm.reflect.PropertyGetter;
//import org.smart.orm.reflect.TableInfo;
//
//public class BetweenExpression<T extends Model<T>> extends WhereExpression<T> {
//
//    private final static String EXPRESSION = " %s %s between ? and ? ";
//
//    private Object value1;
//
//    private Object value2;
//
//    public BetweenExpression() {
//    }
//
//    public BetweenExpression(PropertyGetter<T> property, Object value1, Object value2) {
//        super(WhereType.NONE, property);
//        this.value1 = value1;
//        this.value2 = value2;
//    }
//
//
//    public BetweenExpression(WhereType whereType, PropertyGetter<T> property, Object value1, Object value2) {
//        super(whereType, property);
//        this.value1 = value1;
//        this.value2 = value2;
//
//    }
//
//
//    public Object getValue1() {
//        return value1;
//    }
//
//    public void setValue1(Object value1) {
//        this.value1 = value1;
//    }
//
//    public Object getValue2() {
//        return value2;
//    }
//
//    public void setValue2(Object value2) {
//        this.value2 = value2;
//    }
//
//    @Override
//    protected String build(TableInfo tableInfo, String property) {
//        this.params.clear();
//        this.params.add(value1);
//        this.params.add(value2);
//        return String.op(EXPRESSION, whereText(), property);
//
//    }
//
//
//}

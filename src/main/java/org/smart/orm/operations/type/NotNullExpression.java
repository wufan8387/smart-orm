//package org.smart.orm.operations.type;
//
//import org.smart.orm.Model;
//import org.smart.orm.data.WhereType;
//import org.smart.orm.reflect.PropertyGetter;
//import org.smart.orm.reflect.TableInfo;
//
//public class NotNullExpression<T extends Model<T>> extends WhereExpression<T> {
//
//    public NotNullExpression() {
//    }
//
//
//    public NotNullExpression(PropertyGetter<T> property) {
//        super(WhereType.NONE, property);
//    }
//
//    public NotNullExpression(WhereType whereType, PropertyGetter<T> property) {
//        super(whereType, property);
//    }
//
//    private final static String EXPRESSION = " %s %s is not null ";
//
//
//    @Override
//    protected String build(TableInfo tableInfo, String property) {
//        this.params.clear();
//        return String.op(EXPRESSION, whereText(), property);
//    }
//
//}

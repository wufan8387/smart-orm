//package org.smart.orm.operations.type;
//
//import org.apache.commons.lang3.StringUtils;
//import org.smart.orm.Model;
//import org.smart.orm.data.OperationPriority;
//import org.smart.orm.data.WhereType;
//import org.smart.orm.operations.AbstractExpression;
//import org.smart.orm.reflect.EntityInfo;
//import org.smart.orm.reflect.LambdaParser;
//import org.smart.orm.reflect.PropertyGetter;
//import org.smart.orm.reflect.TableInfo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class WhereExpression<T extends Model<T>> extends AbstractExpression {
//
//
//    protected WhereType whereType = WhereType.NONE;
//
//    protected String property;
//
//
//    private List<WhereExpression<?>> children = new ArrayList<>();
//
//    public WhereExpression() {
//    }
//
//
//    public WhereExpression(PropertyGetter<T> property) {
//        EntityInfo entityInfo = T.getMeta(this.getClass());
//        tableInfo = entityInfo.getTable();
//        this.property = entityInfo
//                .getPropertyMap()
//                .get(LambdaParser.getGetter(property).getName())
//                .getColumnName();
//    }
//
//
//
//    public WhereExpression(WhereType whereType, PropertyGetter<T> property) {
//        EntityInfo entityInfo = T.getMeta(this.getClass());
//        this.tableInfo = entityInfo.getTable();
//        this.whereType = whereType;
//        this.property = entityInfo
//                .getPropertyMap()
//                .get(LambdaParser.getGetter(property).getName())
//                .getColumnName();
//    }
//
//
//
//
//    public WhereType getWhereType() {
//        return whereType;
//    }
//
//    public void setWhereType(WhereType whereType) {
//        this.whereType = whereType;
//    }
//
//    public String getProperty() {
//        return property;
//    }
//
//    public void setProperty(PropertyGetter<T> property) {
//        EntityInfo entityInfo = T.getMeta(this.getClass());
//        this.property = entityInfo
//                .getPropertyMap()
//                .get(LambdaParser.getGetter(property).getName())
//                .getColumnName();
//    }
//
//
//    @Override
//    public String build() {
//        return build(tableInfo, property);
//    }
//
//    protected abstract String build(TableInfo tableInfo, String property);
//
//    @Override
//    public int getPriority() {
//        return OperationPriority.LOGICAL;
//    }
//
//    public <U extends Model<U>> WhereExpression<U> andFor(WhereExpression<U> operation) {
//        operation.setWhereType(WhereType.AND);
//        this.children.add(operation);
//        return operation;
//    }
//
//    public <U extends Model<U>> WhereExpression<U> orFor(WhereExpression<U> operation) {
//        operation.setWhereType(WhereType.OR);
//        statement.add(operation);
//        return operation;
//    }
//
//
//    public WhereExpression<?> andFor(String table, WhereExpression<?> operation) {
//        operation.setWhereType(WhereType.AND);
//        children.add(operation);
//        return operation;
//    }
//
//    public WhereExpression<?> orFor(String table, WhereExpression<?> operation) {
//        operation.setWhereType(WhereType.OR);
//        statement.add(operation);
//        return operation;
//    }
//
//    public WhereExpression<T> and(WhereExpression<T> operation) {
//        operation.setWhereType(WhereType.AND);
//        operation.setTableInfo(tableInfo);
//        children.add(operation);
//        return this;
//    }
//
//    public WhereExpression<T> or(WhereExpression<T> operation) {
//        operation.setWhereType(WhereType.OR);
//        operation.setTableInfo(tableInfo);
//        children.add(operation);
//        return this;
//    }
//
//
//    public LimitNode limit(int count) {
//        return new LimitNode(statement, count);
//    }
//
//    protected String whereText() {
//        switch (whereType) {
//            case AND:
//                return " AND ";
//            case OR:
//                return " OR ";
//            case LOGICAL:
//                return " LOGICAL ";
//        }
//        return StringUtils.EMPTY;
//    }
//
//
//}

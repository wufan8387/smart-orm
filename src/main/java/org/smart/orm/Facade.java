//package org.smart.orm;
//
//import org.smart.orm.data.WhereType;
//import org.smart.orm.operations.Statement;
//import org.smart.orm.operations.type.*;
//import org.smart.orm.reflect.PropertyGetter;
//
//import java.util.UUID;
//
//public class Facade {
//
//    // equal
//    public static <T extends Model<T>> EqualExpression<T> equal(PropertyGetter<T> property, Object
//            value, WhereType whereType) {
//        return new EqualExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> EqualExpression<T> equal(Class<T> cls, PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new EqualExpression<>(whereType, property, value);
//    }
//
//    // not equal
//    public static <T extends Model<T>> NotEqualExpression<T> notEqual(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new NotEqualExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> NotEqualExpression<T> notEqual(Class<T> cls, PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new NotEqualExpression<>(whereType, property, value);
//    }
//
//    // between
//    public static <T extends Model<T>> BetweenExpression<T> between(PropertyGetter<T> property, Object value1, Object value2, WhereType whereType) {
//        return new BetweenExpression<>(whereType, property, value1, value2);
//    }
//
//    public static <T extends Model<T>> BetweenExpression<T> between(Class<T> cls, PropertyGetter<T> property, Object value1, Object value2, WhereType whereType) {
//        return new BetweenExpression<>(whereType, property, value1, value2);
//    }
//
//    // like
//    public static <T extends Model<T>> LikeExpression<T> like(PropertyGetter<T> property, String value, WhereType whereType) {
//        return new LikeExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> LikeExpression<T> like(Class<T> cls, PropertyGetter<T> property, String value, WhereType whereType) {
//        return new LikeExpression<>(whereType, property, value);
//    }
//
//    // great than
//    public static <T extends Model<T>> GreatThanExpression<T> greatThan(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new GreatThanExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> GreatThanExpression<T> greatThan(Class<T> cls, PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new GreatThanExpression<>(whereType, property, value);
//    }
//
//    // less than
//    public static <T extends Model<T>> LessThanExpression<T> lessThan(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new LessThanExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> LessThanExpression<T> lessThan(Class<T> cls, PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new LessThanExpression<>(whereType, property, value);
//    }
//
//    // greate than equal
//    public static <T extends Model<T>> GreatThanEqualExpression<T> greateThanEqual(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new GreatThanEqualExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> GreatThanEqualExpression<T> greateThanEqual(Class<T> cls, PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new GreatThanEqualExpression<>(whereType, property, value);
//    }
//
//    // less than equal
//    public static <T extends Model<T>> LessThanEqualExpression<T> lessThanEqual(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new LessThanEqualExpression<>(whereType, property, value);
//    }
//
//    public static <T extends Model<T>> LessThanEqualExpression<T> lessThanEqual(Class<T> cls, PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new LessThanEqualExpression<>(whereType, property, value);
//    }
//
//    // is null
//    public static <T extends Model<T>> IsNullExpression<T> isNull(PropertyGetter<T> property, WhereType whereType) {
//        return new IsNullExpression<>(whereType, property);
//    }
//
//    // not null
//    public static <T extends Model<T>> NotNullExpression<T> notNull(PropertyGetter<T> property, WhereType whereType) {
//        return new NotNullExpression<>(whereType, property);
//    }
//
//    // in
//    public static <T extends Model<T>> InExpression<T> in(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new InExpression<>(whereType, property, value);
//    }
//
//    // not in
//    public static <T extends Model<T>> NotInExpression<T> notIn(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new NotInExpression<>(whereType, property, value);
//    }
//
//    // exists
//    public static <T extends Model<T>> ExistsExpression<T> exists(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new ExistsExpression<>(whereType, property, value);
//    }
//
//    // not exists
//    public static <T extends Model<T>> NotExistsExpression<T> notExists(PropertyGetter<T> property, Object value, WhereType whereType) {
//        return new NotExistsExpression<>(whereType, property, value);
//    }
//
//    // insert
//    public static <T extends Model<T>> InsertStatement<T> insert(Statement statement, T entity) {
//        return new InsertStatement<>(statement);
//    }
//
//    // delete
//    public static <T extends Model<T>> DeleteStatement<T> delete(Statement statement, T entity) {
//        return new DeleteStatement<>(statement);
//    }
//
//    // update
//    public static <T extends Model<T>> UpdateStatement<T> update(Statement statement) {
//        return new UpdateStatement<>(statement);
//    }
//
//    public static <T extends Model<T>> UpdateStatement<T> update(Statement statement, T entity) {
//        return new UpdateStatement<>(statement, entity);
//    }
//
//    // select
//    public static <T extends Model<T>> AttributeNode<T> select(Statement statement) {
//        return new AttributeNode<>(statement);
//    }
//
//}

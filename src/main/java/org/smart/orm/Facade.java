package org.smart.orm;

import org.smart.orm.data.WhereType;
import org.smart.orm.operations.*;
import org.smart.orm.reflect.PropertyGetter;

import java.util.UUID;

public class Facade {

    // equal
    public static <T extends Model<T>> EqualOperation<T> equal(PropertyGetter<T> property, Object value, WhereType whereType) {
        return new EqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> EqualOperation<T> equal(Class<T> cls, PropertyGetter<T> property, Object value,
                                                               WhereType whereType) {
        return new EqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> EqualOperation<T> equal(String property, Object value, WhereType whereType) {
        return new EqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> EqualOperation<T> equal(Class<T> cls, String property, Object value,
            WhereType whereType) {
        return new EqualOperation<>(whereType, property, value);
    }

    // not equal
    public static <T extends Model<T>> NotEqualOperation<T> notEqual(PropertyGetter<T> property, Object value,
                                                                     WhereType whereType) {
        return new NotEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> NotEqualOperation<T> notEqual(Class<T> cls, PropertyGetter<T> property, Object value,
                                                                     WhereType whereType) {
        return new NotEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> NotEqualOperation<T> notequal(String property, Object value,
            WhereType whereType) {
        return new NotEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> NotEqualOperation<T> notequal(Class<T> cls, String property, Object value,
            WhereType whereType) {
        return new NotEqualOperation<>(whereType, property, value);
    }

    // between
    public static <T extends Model<T>> BetweenOperation<T> between(PropertyGetter<T> property, Object value1, Object value2,
                                                                   WhereType whereType) {
        return new BetweenOperation<>(whereType, property, value1, value2);
    }

    public static <T extends Model<T>> BetweenOperation<T> between(Class<T> cls, PropertyGetter<T> property, Object value1,
                                                                   Object value2, WhereType whereType) {
        return new BetweenOperation<>(whereType, property, value1, value2);
    }

    public static <T extends Model<T>> BetweenOperation<T> between(String property, Object value1, Object value2,
            WhereType whereType) {
        return new BetweenOperation<>(whereType, property, value1, value2);
    }

    public static <T extends Model<T>> BetweenOperation<T> between(Class<T> cls, String property, Object value1,
            Object value2, WhereType whereType) {
        return new BetweenOperation<>(whereType, property, value1, value2);
    }

    // like
    public static <T extends Model<T>> LikeOperation<T> like(PropertyGetter<T> property, String value, WhereType whereType) {
        return new LikeOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LikeOperation<T> like(Class<T> cls, PropertyGetter<T> property, String value,
                                                             WhereType whereType) {
        return new LikeOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LikeOperation<T> like(String property, String value, WhereType whereType) {
        return new LikeOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LikeOperation<T> like(Class<T> cls, String property, String value,
            WhereType whereType) {
        return new LikeOperation<>(whereType, property, value);
    }

    // great than
    public static <T extends Model<T>> GreatThanOperation<T> greatThan(PropertyGetter<T> property, Object value,
                                                                       WhereType whereType) {
        return new GreatThanOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> GreatThanOperation<T> greatThan(Class<T> cls, PropertyGetter<T> property, Object value,
                                                                       WhereType whereType) {
        return new GreatThanOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> GreatThanOperation<T> greatThan(String property, Object value,
            WhereType whereType) {
        return new GreatThanOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> GreatThanOperation<T> greatThan(Class<T> cls, String property, Object value,
            WhereType whereType) {
        return new GreatThanOperation<>(whereType, property, value);
    }

    // less than
    public static <T extends Model<T>> LessThanOperation<T> lessThan(PropertyGetter<T> property, Object value,
                                                                     WhereType whereType) {
        return new LessThanOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LessThanOperation<T> lessThan(Class<T> cls, PropertyGetter<T> property, Object value,
                                                                     WhereType whereType) {
        return new LessThanOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LessThanOperation<T> lessThan(String property, Object value,
            WhereType whereType) {
        return new LessThanOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LessThanOperation<T> lessThan(Class<T> cls, String property, Object value,
            WhereType whereType) {
        return new LessThanOperation<>(whereType, property, value);
    }

    // greate than equal
    public static <T extends Model<T>> GreatThanEqualOperation<T> greateThanEqual(PropertyGetter<T> property, Object value,
                                                                                  WhereType whereType) {
        return new GreatThanEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> GreatThanEqualOperation<T> greateThanEqual(Class<T> cls, PropertyGetter<T> property,
            Object value, WhereType whereType) {
        return new GreatThanEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> GreatThanEqualOperation<T> greateThanEqual(String property, Object value,
            WhereType whereType) {
        return new GreatThanEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> GreatThanEqualOperation<T> greateThanEqual(Class<T> cls, String property,
            Object value, WhereType whereType) {
        return new GreatThanEqualOperation<>(whereType, property, value);
    }

    // less than equal
    public static <T extends Model<T>> LessThanEqualOperation<T> lessThanEqual(PropertyGetter<T> property, Object value,
                                                                               WhereType whereType) {
        return new LessThanEqualOperation<T>(whereType, property, value);
    }

    public static <T extends Model<T>> LessThanEqualOperation<T> lessThanEqual(Class<T> cls, PropertyGetter<T> property,
            Object value, WhereType whereType) {
        return new LessThanEqualOperation<T>(whereType, property, value);
    }

    public static <T extends Model<T>> LessThanEqualOperation<T> lessThanEqual(String property, Object value,
            WhereType whereType) {
        return new LessThanEqualOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> LessThanEqualOperation<T> lessThanEqual(Class<T> cls, String property,
            Object value, WhereType whereType) {
        return new LessThanEqualOperation<>(whereType, property, value);
    }

    // is null
    public static <T extends Model<T>> IsNullOperation<T> isNull(PropertyGetter<T> property, WhereType whereType) {
        return new IsNullOperation<T>(whereType, property);
    }

    public static <T extends Model<T>> IsNullOperation<T> isNull(Class<T> cls, String property, WhereType whereType) {
        return new IsNullOperation<T>(whereType, property);
    }

    // not null
    public static <T extends Model<T>> NotNullOperation<T> notNull(PropertyGetter<T> property, WhereType whereType) {
        return new NotNullOperation<T>(whereType, property);
    }

    public static <T extends Model<T>> NotNullOperation<T> notNull(Class<T> cls, String property, WhereType whereType) {
        return new NotNullOperation<T>(whereType, property);
    }

    // in
    public static <T extends Model<T>> InOperation<T> in(PropertyGetter<T> property, Object value, WhereType whereType) {
        return new InOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> InOperation<T> in(Class<T> cls, String property, Object value,
            WhereType whereType) {
        return new InOperation<>(whereType, property, value);
    }

    // not in
    public static <T extends Model<T>> NotInOperation<T> notIn(PropertyGetter<T> property, Object value, WhereType whereType) {
        return new NotInOperation<T>(whereType, property, value);
    }

    public static <T extends Model<T>> NotInOperation<T> notIn(String property, Object value, WhereType whereType) {
        return new NotInOperation<>(whereType, property, value);
    }

    // exists
    public static <T extends Model<T>> ExistsOperation<T> exists(PropertyGetter<T> property, Object value,
                                                                 WhereType whereType) {
        return new ExistsOperation<>(whereType, property, value);
    }

    public static <T extends Model<T>> ExistsOperation<T> exists(String property, Object value, WhereType whereType) {
        return new ExistsOperation<>(whereType, property, value);
    }

    // not exists
    public static <T extends Model<T>> NotExistsOperation<T> notExists(PropertyGetter<T> property, Object value,
                                                                       WhereType whereType) {
        return new NotExistsOperation<T>(whereType, property, value);
    }

    public static <T extends Model<T>> NotExistsOperation<T> notExists(String property, Object value,
            WhereType whereType) {
        return new NotExistsOperation<>(whereType, property, value);
    }

    // insert
    public static <T extends Model<T>> InsertOperation<T> insert(OperationContext context, T entity) {
        return new InsertOperation<T>(context, entity.getMeta().getTable());
    }

    // delete
    public static <T extends Model<T>> DeleteOperation<T> delete(OperationContext context, T entity) {
        return new DeleteOperation<>(context, entity.getMeta().getTable());
    }

    // update
    public static <T extends Model<T>> UpdateOperation<T> update(OperationContext context) {
        return new UpdateOperation<>(context);
    }

    public static <T extends Model<T>> UpdateOperation<T> update(OperationContext context, T entity) {
        return new UpdateOperation<>(context, entity);
    }

    // select
    public static <T extends Model<T>> SelectOperation<T> select(OperationContext context) {
        return new SelectOperation<>(UUID.randomUUID(),context);
    }

}

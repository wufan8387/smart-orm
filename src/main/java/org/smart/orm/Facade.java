package org.smart.orm;

import org.smart.orm.operations.*;
import org.smart.orm.reflect.EntityInfo;
import org.smart.orm.reflect.Getter;

public class Facade {
    
    //    equal
    public static <T extends Model<T>> EqualOperation<T> equal(Getter<T> property, Object value, WhereType whereType) {
        return new EqualOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> EqualOperation<T> equal(Class<T> cls, String property, Object value, WhereType whereType) {
        return new EqualOperation<>(whereType, property, value);
    }
    
    //    not equal
    public static <T extends Model<T>> NotEqualOperation<T> notEqual(Getter<T> property, Object value, WhereType whereType) {
        return new NotEqualOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> NotEqualOperation<T> notequal(Class<T> cls, String property, Object value, WhereType whereType) {
        return new NotEqualOperation<>(whereType, property, value);
    }
    
    //    between
    public static <T extends Model<T>> BetweenOperation<T> between(Getter<T> property, Object value1, Object value2, WhereType whereType) {
        return new BetweenOperation<>(whereType, property, value1, value2);
    }
    
    public static <T extends Model<T>> BetweenOperation<T> between(Class<T> cls, String property, Object value1, Object value2, WhereType whereType) {
        return new BetweenOperation<>(whereType, property, value1, value2);
    }
    
    
    //    like
    public static <T extends Model<T>> LikeOperation<T> like(Getter<T> property, Object value, WhereType whereType) {
        return new LikeOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> LikeOperation<T> like(Class<T> cls, String property, Object value, WhereType whereType) {
        return new LikeOperation<>(whereType, property, value);
    }
    
    //    great than
    public static <T extends Model<T>> GreatThanOperation<T> greatThan(Getter<T> property, Object value, WhereType whereType) {
        return new GreatThanOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> GreatThanOperation<T> greatThan(Class<T> cls, String property, Object value, WhereType whereType) {
        return new GreatThanOperation<>(whereType, property, value);
    }
    
    //    less than
    public static <T extends Model<T>> LessThanOperation<T> lessThan(Getter<T> property, Object value, WhereType whereType) {
        return new LessThanOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> LessThanOperation<T> lessThan(Class<T> cls, String property, Object value, WhereType whereType) {
        return new LessThanOperation<>(whereType, property, value);
    }
    
    //    greate than equal
    public static <T extends Model<T>> GreatThanEqualOperation<T> greateThanEqual(Getter<T> property, Object value, WhereType whereType) {
        return new GreatThanEqualOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> GreatThanEqualOperation<T> greateThanEqual(Class<T> cls, String property, Object value, WhereType whereType) {
        return new GreatThanEqualOperation<>(whereType, property, value);
    }
    
    //    less than equal
    public static <T extends Model<T>> LessThanEqualOperation<T> lessThanEqual(Getter<T> property, Object value, WhereType whereType) {
        return new LessThanEqualOperation<T>(whereType, property, value);
    }
    
    public static <T extends Model<T>> LessThanEqualOperation<T> lessThanEqual(Class<T> cls, String property, Object value, WhereType whereType) {
        return new LessThanEqualOperation<>(whereType, property, value);
    }
    
    //    is null
    public static <T extends Model<T>> IsNullOperation<T> isNull(Getter<T> property, WhereType whereType) {
        return new IsNullOperation<T>(whereType, property);
    }
    
    public static <T extends Model<T>> IsNullOperation<T> isNull(Class<T> cls, String property, WhereType whereType) {
        return new IsNullOperation<T>(whereType, property);
    }
    
    //    not null
    public static <T extends Model<T>> NotNullOperation<T> notNull(Getter<T> property, WhereType whereType) {
        return new NotNullOperation<T>(whereType, property);
    }
    
    public static <T extends Model<T>> NotNullOperation<T> notNull(Class<T> cls, String property, WhereType whereType) {
        return new NotNullOperation<T>(whereType, property);
    }
    
    //    in
    public static <T extends Model<T>> InOperation<T> in(Getter<T> property, Object value, WhereType whereType) {
        return new InOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> InOperation<T> in(Class<T> cls, String property, Object value, WhereType whereType) {
        return new InOperation<>(whereType, property, value);
    }
    
    //    not in
    public static <T extends Model<T>> NotInOperation<T> notIn(Getter<T> property, Object value, WhereType whereType) {
        return new NotInOperation<T>(whereType, property, value);
    }
    
    public static <T extends Model<T>> NotInOperation<T> notIn(Class<T> cls, String property, Object value, WhereType whereType) {
        return new NotInOperation<>(whereType, property, value);
    }
    
    //    exists
    public static <T extends Model<T>> ExistsOperation<T> exists(Getter<T> property, Object value, WhereType whereType) {
        return new ExistsOperation<>(whereType, property, value);
    }
    
    public static <T extends Model<T>> ExistsOperation<T> exists(Class<T> cls, String property, Object value, WhereType whereType) {
        return new ExistsOperation<>(whereType, property, value);
    }
    
    //    not exists
    public static <T extends Model<T>> NotExistsOperation<T> notExists(Getter<T> property, Object value, WhereType whereType) {
        return new NotExistsOperation<T>(whereType, property, value);
    }
    
    public static <T extends Model<T>> NotExistsOperation<T> notExists(Class<T> cls, String property, Object value, WhereType whereType) {
        return new NotExistsOperation<>(whereType, property, value);
    }
    
    //    insert
    public static <T extends Model<T>> InsertOperation<T> insert(T model) {
        
        
        return new InsertOperation<>(model);
    }
    
    //    delete
    public static <T extends Model<T>> DeleteOperation<T> delete(Class<T> cls) {
        
        EntityInfo entityInfo = T.getMetaMap().get(cls.getName());
        
        return new DeleteOperation<>(entityInfo);
    }
    
    
    //    select
    public static <T extends Model<T>> SelectOperation<T> select(Class<T> cls) {
        
        EntityInfo entityInfo = Model.getMetaMap().get(cls.getName());
        
        return new SelectOperation<>(entityInfo);
    }
    
    //    update
    public static <T extends Model<T>> UpdateOperation<T> update(Class<T> cls) {
        
        EntityInfo entityInfo = Model.getMetaMap().get(cls.getName());
        
        return new UpdateOperation<>(entityInfo);
    }
    
    
}

package org.smart.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    
    String value() default "";
    
    ColumnFillType[] fillType() default {};
    
    boolean isPrimaryKey() default false;
    
    IdType idType() default IdType.Manual;
    
    
}

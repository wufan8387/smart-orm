package org.smart.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
public @interface Column {
    
    String name() default "";
    
    ColumnFillType[] fillType();
    
    boolean isPrimaryKey() default false;
    
    IdType idType();
    
}

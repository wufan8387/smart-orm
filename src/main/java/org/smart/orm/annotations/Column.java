package org.smart.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
public @interface Column {
    
    String value() default "";
    
    ColumnFillType[] fillType() default {};
    
    boolean isPrimaryKey() default false;
    
    IdType idType() default IdType.Manual;
    
    
}

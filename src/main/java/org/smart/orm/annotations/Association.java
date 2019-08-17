package org.smart.orm.annotations;

import org.smart.orm.data.AssociationType;
import org.smart.orm.data.FetchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Association {
    
    String thisKey();
    
    String otherKey();
    
    Class<?> otherEntity();
    
    FetchType fetch() default FetchType.ON_DEMAND;
    
    AssociationType type();
}

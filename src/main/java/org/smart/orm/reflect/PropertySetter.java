package org.smart.orm.reflect;

import java.io.Serializable;

@FunctionalInterface
public interface PropertySetter<T, U> extends Serializable {
    void accept(T t, U u);
}

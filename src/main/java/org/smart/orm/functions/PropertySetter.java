package org.smart.orm.functions;

import java.io.Serializable;

@FunctionalInterface
public interface PropertySetter<T, U> extends Serializable {
    void accept(T t, U u);
}

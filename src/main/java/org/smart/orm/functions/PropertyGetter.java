package org.smart.orm.functions;

import java.io.Serializable;

@FunctionalInterface
public interface PropertyGetter<T> extends Serializable {
    Object apply(T source);
}

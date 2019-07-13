package org.smart.orm.reflect;

import java.io.Serializable;

@FunctionalInterface
public interface PropertyGetter<T> extends Serializable {
    Object apply(T source);
}

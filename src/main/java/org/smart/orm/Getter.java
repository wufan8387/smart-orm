package org.smart.orm;

import java.io.Serializable;

@FunctionalInterface
public interface Getter<T> extends Serializable {
    Object apply(T source);
}

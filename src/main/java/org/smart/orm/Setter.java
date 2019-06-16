package org.smart.orm;

import java.io.Serializable;

@FunctionalInterface
public interface Setter<T, U> extends Serializable {
    void accept(T t, U u);
}

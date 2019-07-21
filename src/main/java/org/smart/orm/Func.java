package org.smart.orm;

@FunctionalInterface
public interface Func<T> {
    T apply(Object... args);
}

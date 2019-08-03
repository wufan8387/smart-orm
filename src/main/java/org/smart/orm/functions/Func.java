package org.smart.orm.functions;

@FunctionalInterface
public interface Func<T> {
    T apply(Object... args);
}

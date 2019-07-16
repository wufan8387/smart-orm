package org.smart.orm.operations;


@FunctionalInterface
public interface Formatter {
    
    
    String format(Object... args);
    
    
}

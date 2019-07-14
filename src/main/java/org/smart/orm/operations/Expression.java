package org.smart.orm.operations;


@FunctionalInterface
public interface Expression {
    
    
    String generate(Object... args);
    
    
}

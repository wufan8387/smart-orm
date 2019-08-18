package org.smart.orm.operations;


import org.smart.orm.data.StatementType;
import org.smart.orm.execution.Executor;
import org.smart.orm.execution.ResultData;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Statement {
    
    UUID getId();
    
    List<Object> getParams();
    
    List<SqlNode<?, ?>> getNodes();
    
    StatementType getType();
    
    <T> ResultData<T> getResult();
    
    <T extends SqlNode<?, ?>> T attach(T node);
    
    <T extends SqlNode<?, ?>> List<T> find(int nodeType, Predicate<T> predicate);
    
    <T extends SqlNode<?, ?>> T findFirst(int nodeType, Predicate<T> predicate);
    
    <T extends SqlNode<?, ?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other);
    
    void execute(Executor executor);
    
}

package org.smart.orm.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractStatement implements Statement {
    
    private UUID id = UUID.randomUUID();
    
    private final List<SqlNode<?, ?>> nodeList = new ArrayList<>();
    
    private List<Object> paramList = new ArrayList<>();
    
    
    @Override
    public UUID getId() {
        return id;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?, ?>> List<T> find(int nodeType, Predicate<T> predicate) {
        return nodeList.stream()
                .filter(t -> t.getType() == nodeType)
                .map(t -> (T) t)
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    public <T extends SqlNode<?, ?>> T findFirst(int nodeType, Predicate<T> predicate) {
        return findFirst(nodeType, predicate, () -> null);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?, ?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other) {
        return nodeList.stream()
                .filter(t -> t.getType() == nodeType)
                .map(t -> (T) t)
                .filter(predicate)
                .findFirst()
                .orElseGet(other);
    }
    
    
    protected abstract <T extends SqlNode<?, ?>> void doAttach(T node);
    
    @Override
    public <T extends SqlNode<?, ?>> T attach(T node) {
        doAttach(node);
        if (!nodeList.contains(node))
            nodeList.add(node);
        return node;
    }
    
    @Override
    public List<Object> getParams() {
        return paramList;
    }
    
    @Override
    public List<SqlNode<?, ?>> getNodes() {
        return nodeList;
    }
}

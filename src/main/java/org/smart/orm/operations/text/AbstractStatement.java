package org.smart.orm.operations.text;

import org.apache.commons.lang3.StringUtils;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractStatement implements Statement {
    
    private UUID id = UUID.randomUUID();
    
    private int sn = 0;
    
    protected final List<SqlNode<?>> nodeList = new ArrayList<>();
    
    protected List paramList = new ArrayList();
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> List<T> find(int nodeType, Predicate<T> predicate) {
        return nodeList.stream()
                .filter(t -> t.getType() == nodeType)
                .map(t -> (T) t)
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate) {
        return findFirst(nodeType, predicate, () -> null);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other) {
        return nodeList.stream()
                .filter(t -> t.getType() == nodeType)
                .map(t -> (T) t)
                .filter(predicate)
                .findFirst()
                .orElseGet(other);
    }
    
    @Override
    public String alias(String term) {
        sn++;
        return term + "_" + sn;
    }
    
    @Override
    public UUID getId() {
        return id;
    }
    
    @Override
    public List getParams() {
        return paramList;
    }
    

}

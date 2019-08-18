package org.smart.orm.execution;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultData<T> {
    
    private int count;
    
    private List<T> list = new ArrayList<>();
    
    public ResultData(int count) {
        this.count = count;
    }
    
    public ResultData(List<T> list) {
        this.list.addAll(list);
    }
    
    public ResultData(int count, List<T> list) {
        this.count = count;
        this.list.addAll(list);
    }
    
    
    
    public int getCount() {
        return count;
    }
    
    public Optional<T> first() {
        if (list.size() > 0)
            return Optional.of(list.get(0));
        return Optional.empty();
    }
    
    public List<T> all() {
        return list;
    }
}

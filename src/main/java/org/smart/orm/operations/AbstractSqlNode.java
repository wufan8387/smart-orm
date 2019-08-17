package org.smart.orm.operations;



import java.util.function.Supplier;

public abstract class AbstractSqlNode<T extends Statement, K extends SqlNode<T, K>> implements SqlNode<T, K> {
    
    private Supplier<Object[]> params;
    
    private T stat;
    
    @SuppressWarnings("unchecked")
    @Override
    public K attach(T statement) {
        stat = statement;
        stat.attach(this);
        return (K) this;
    }
    
    @Override
    public T statement() {
        return stat;
    }
    
    public void setParams(Object[] params) {
        this.params = () -> params;
    }
    
    public void setParams(Supplier<Object[]> params) {
        this.params = params;
    }
    
    @Override
    public Supplier<Object[]> getParams() {
        return params;
    }
}

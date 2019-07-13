package org.smart.orm.data;


public final class Tuple<K, V> {
    
    public final K key;
    
    public final V value;
    
    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    
}

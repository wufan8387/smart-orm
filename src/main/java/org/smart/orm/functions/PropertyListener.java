package org.smart.orm.functions;

import org.smart.orm.Model;

public interface PropertyListener {
    
    void onChange(Model<?> model, String property, Object value);
    
}

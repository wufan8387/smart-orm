package org.smart.orm;

import java.io.Serializable;

public class SmartORMException extends RuntimeException implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public SmartORMException() {
    
    }
    
    public SmartORMException(Exception ex) {
        super(ex);
    }
    
    public SmartORMException(String message) {
        super(message);
    }
    
    public SmartORMException(String message, Exception ex) {
        super(message, ex);
    }
    
}

package org.smart.orm;

public class SmartORMException extends RuntimeException {

    public SmartORMException() {

    }

    public SmartORMException(Exception ex) {
        super(ex);
    }

    public SmartORMException(String message) {
        super(message);
    }

}

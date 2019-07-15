package org.smart.orm.data;

public interface OperationPriority {
    
    public final static int UPDATE = 0;
    public final static int DELETE = 0;
    public final static int INSERT = 0;
    
    public final static int SELECT = 2;
    
    public final static int FROM = 3;
    
    public final static int JOIN = 4;
    
    public final static int WHERE = 5;
    
    public final static int ORDERBY = 6;
    
    public final static int LIMIT = 7;
    
    
    public final static int INCLUDE = 101;
    
}

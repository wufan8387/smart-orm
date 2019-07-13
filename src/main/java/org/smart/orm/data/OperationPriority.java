package org.smart.orm.data;

public interface OperationPriority {
    
    public final static int SELECT = 0;
    public final static int UPDATE = 0;
    public final static int DELETE = 0;
    public final static int INSERT = 0;
    
    public final static int FROM = 1;
    
    public final static int JOIN = 2;
    
    public final static int WHERE = 3;
    
    public final static int WHERE_N = 4;
    
    public final static int LIMIT = 5;
    
    public final static int ORDERBY = 5;
    
    
    public final static int INCLUDE = 101;
    
}

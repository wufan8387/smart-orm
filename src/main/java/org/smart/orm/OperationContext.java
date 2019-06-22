package org.smart.orm;

import org.smart.orm.reflect.Getter;

import java.util.ArrayList;
import java.util.List;

public class OperationContext {
    
    private List<Operation> operationList = new ArrayList<>();
    
    public List<Operation> getOperationList() {
        return operationList;
    }
    
    public void add(Operation operation) {
        this.operationList.add(operation);
    }
    
}

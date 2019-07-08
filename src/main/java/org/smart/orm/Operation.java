package org.smart.orm;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Operation {

    UUID getBatch();

    void setBatch(UUID batch);

    int getPriority();

    String getExpression();

    void build();
    
    List<Object> getParams();

    OperationContext getContext();

}

package org.smart.orm;

import java.util.Collection;
import java.util.UUID;

public interface Operation {

    UUID getBatch();

    UUID setBatch(UUID batch);

    int getPriority();

    String getExpression();

    void build();

    Collection<Object> getParams();

    OperationContext getContext();

}

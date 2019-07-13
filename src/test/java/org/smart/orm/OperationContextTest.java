package org.smart.orm;

import org.junit.Assert;
import org.junit.Test;
import org.smart.orm.operations.EqualOperation;
import org.smart.orm.operations.FromOperation;
import org.smart.orm.operations.LikeOperation;

import java.util.UUID;

import static org.junit.Assert.*;

public class OperationContextTest {
    
    @Test
    public void buildTEst() {
        
        OperationContext context = new OperationContext();
        
        FromOperation<?> fromOperation = new FromOperation(UUID.randomUUID(), context, "user");
        
        fromOperation
                .select("id", "name")
                .where(new EqualOperation<>("id", 100))
                .andThis(new LikeOperation<>("name", "100"));
        
        
        OperationContext.ExecuteData data = context.build(fromOperation.getBatch());
        
        System.out.print(data.sql);
        
        
    }
    
    
}
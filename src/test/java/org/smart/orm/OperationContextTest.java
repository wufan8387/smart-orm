package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.text.*;

import java.util.UUID;

public class OperationContextTest {
    
    @Test
    public void buildTest() {
        
        OperationContext context = new OperationContext();
        
        FromOperation fromOperation = new FromOperation(UUID.randomUUID(), context, "user");
        
        fromOperation
                .select("id", "name")
                .join(new JoinOperation("profile"))
                .on("id", new EqualOperation("profile", "id", "100"))
                .where("user", new EqualOperation("id", 100))
                .and(new LikeOperation("name", "%100%"))
                .andFor("user", new NotNullOperation("name"));
        
        
        OperationContext.ExecuteData data = context.build(fromOperation.getBatch());
        
        System.out.print(data.sql);
        
        
    }
    
    
}
package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.text.*;

import java.util.UUID;

public class TextOperationTest {
    
    @Test
    public void buildTest() {
        
        OperationContext context = new OperationContext();
        
        FromOperation fromOperation = new FromOperation(UUID.randomUUID(), context, "user");
        
        fromOperation
                .select("id", "name")
                .join(new JoinOperation("profile"))
                .on("id", new EqualOperation("user", "id"))
                .and("name",new NotEqualOperation("user","name"))
                .where(new EqualOperation("user", "id"))
                .and(new LikeOperation("name", "%100%"))
                .andFor("user", new NotNullOperation("name"));
        
        
        OperationContext.ExecuteData data = context.build(fromOperation.getBatch());
        
        System.out.print(data.sql);
        
        
    }
    
    
}
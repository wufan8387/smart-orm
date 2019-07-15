package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.Exp;
import org.smart.orm.operations.text.*;

import java.util.UUID;

public class TextOperationTest {
    
    @Test
    public void buildTest() {
        
        OperationContext context = new OperationContext();
        
        FromOperation fromOperation = new FromOperation(UUID.randomUUID(), context, "user");
        
        fromOperation
                .select("id", "name")
//                .join(new JoinOperation("profile"))
//                .on("id", new EqualOperation("user", "id"))
//                .and("name", new NotEqualOperation("user", "name"))
                .where("id", Exp.EQUAL, 100)
                .and("name", Exp.LIKE, "%100%")
                .or("id", Exp.EQUAL, 100)
                .and("user", "name", Exp.IS_NULL)
                .or("user", "name", Exp.NOT_nULL);
        
        
        OperationContext.ExecuteData data = context.build(fromOperation.getBatch());
        
        System.out.print(data.sql);
        
        
    }
    
    
}
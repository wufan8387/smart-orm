package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.DefaultStatement;
import org.smart.orm.operations.Exp;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.text.*;

import java.util.UUID;

public class TextExpressionTest {
    
    @Test
    public void buildTest() {
        
        OperationContext context = new OperationContext();
    
        Statement statement=new DefaultStatement();
        FromExpression fromOperation = new FromExpression(statement, "user");
        
        fromOperation
                .select("id", "name")
//                .join(new JoinExpression("profile"))
//                .on("id", new EqualExpression("user", "id"))
//                .and("name", new NotEqualExpression("user", "name"))
                .where("id", Exp.EQUAL, 100)
                .and("name", Exp.LIKE, "%100%")
                .or("id", Exp.EQUAL, 100)
                .and("user", "name", Exp.IS_NULL)
                .or("user", "name", Exp.NOT_nULL);
        
        
//        OperationContext.ExecuteData data = context.build(fromOperation.getBatch());
        
//        System.out.print(data.sql);
        
        
    }
    
    
}
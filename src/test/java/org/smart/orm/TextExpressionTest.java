package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.text.QueryStatement;

public class TextExpressionTest {
    
    @Test
    public void buildTest() {
        
        QueryStatement statement = new QueryStatement();
        
        statement
                .select("name")
                .from("user", "u")
                .join("profile", "p")
                .on("id", Op.EQUAL, "tid")
                .and("x", Op.NOT_EQUAL, "tt")
                .statement()
                .where("user", "id", Op.EQUAL, 100)
                .statement()
                .orderBy("user", "id")
                .desc("profile", "name")
                .statement()
                .limit(100)
                .setEnd(200);
        
        System.out.print(statement.toString());
        System.out.print(statement.getParams().toString());
    
    
    }
    
    
}
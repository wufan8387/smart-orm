package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.type.QueryObject;

public class TypeStatementTEst {
    
    
    @Test
    public void buildTest() {
        QueryObject statement = new QueryObject();
        statement
                .from(Account.class)
                .select(Account::getId)
                .join(Order.class)
                .on(Account::getId, Op.EQUAL, Order::getUid)
                .statement()
                .where(Order::getUid, Op.EQUAL, 100);
        
        System.out.print(statement.toString());
        System.out.print(statement.getParams());
    }
    
    
}

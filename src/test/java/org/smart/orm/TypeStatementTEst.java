package org.smart.orm;

import org.junit.Test;
import org.smart.orm.operations.type.QueryStatement;

import java.sql.SQLException;

public class TypeStatementTEst {
    
    
    @Test
    public void buildTest() throws ClassNotFoundException, SQLException {
        QueryStatement statement = new QueryStatement();
        statement
                .from(TestEntity.class)
                .select(TestEntity::getId);
    }
    
}

package org.smart.orm;

import org.junit.Test;
import org.smart.orm.execution.*;
import org.smart.orm.jdbc.ParameterTypeHandler;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.type.QueryObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TypeStatementTEst {
    
    
    @Test
    public void buildTest() {
        QueryObject statement = new QueryObject();
        statement
                .from(AuthGroup.class)
                .select(AuthGroup::getId)
                .join(Order.class)
                .on(AuthGroup::getId, Op.EQUAL, Order::getUid)
                .statement()
                .where(Order::getUid, Op.EQUAL, 100);
        
        System.out.print(statement.toString());
        System.out.print(statement.getParams());
    }
    
    @Test
    public void modelTest() throws SQLException, ClassNotFoundException {
        
        
        QueryObject statement = new QueryObject();
        statement
                .from(AuthGroup.class)
                .select(AuthGroup::getId)
                .select(AuthGroup::getType)
                .select(AuthGroup::getTitle)
                .select(AuthGroup::getModule)
                .statement();
//                .where(AuthGroup::getModule, Op.EQUAL, "admin");
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connStr = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(connStr, "root", "11301130");
    
        SimpleExecutor executor = new SimpleExecutor();
        executor.setParameterTypeHandler(new ParameterTypeHandler());
        ModelHandler<AuthGroup> handler = new ModelHandler<>(AuthGroup.class);
        handler.addListener(System.out::print);
        executor.executeQuery(conn, handler, statement.toString(), statement.getParams().toArray());
        
        
    }
    
    
}

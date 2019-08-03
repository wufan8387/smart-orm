package org.smart.orm;

import org.junit.Test;
import org.smart.orm.execution.SimpleExecutor;
import org.smart.orm.operations.text.QueryStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TextExpressionTest {
    
    @Test
    public void buildTest() throws ClassNotFoundException, SQLException {
        
        QueryStatement statement = new QueryStatement();
        
        statement
                .select("author_id", "uid")
                .from("mto_posts_anthology", "ag");
        
//        OperationContext context = new OperationContext();
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mayflower?useSSL=false&serverTimezone=UTC",
//                "root", "11301130");
//
//        context.setExecutor(new SimpleExecutor());
//        context.query(AuthGroup.class,conn, statement);
//
//        System.out.print(statement.toString());
//        System.out.print(statement.getParams().toString());
        
        
    }
    
    
}
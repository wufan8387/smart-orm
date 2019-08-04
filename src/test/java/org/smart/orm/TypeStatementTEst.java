package org.smart.orm;

import org.junit.Before;
import org.junit.Test;
import org.smart.orm.data.NodeType;
import org.smart.orm.execution.*;
import org.smart.orm.jdbc.ParameterTypeHandler;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.type.*;
import sun.security.jca.GetInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TypeStatementTest {
    
    private Connection conn;
    private OperationContext context;
    
    @Before
    public void before() throws SQLException, ClassNotFoundException {
        
        
        SimpleExecutor executor = new SimpleExecutor();
        executor.setParameterTypeHandler(new ParameterTypeHandler());
        context = new OperationContext();
        context.setExecutor(executor);
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connStr = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(connStr, "root", "11301130");
    }
    
    @Test
    public void buildTest() {
        QueryObject<AuthGroup> statement = new QueryObject(AuthGroup.class);
        statement
                .select(AuthGroup::getId)
                .statement()
                .join(Order.class)
                .on(AuthGroup::getId, Op.EQUAL, Order::getUid)
                .statement()
                .where(Order::getUid, Op.EQUAL, 100);
        
        System.out.print(statement.toString());
        System.out.print(statement.getParams());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void queryTest() {
        
        QueryObject<AuthGroup> statement = new QueryObject(AuthGroup.class);
        statement
                .where(AuthGroup::getModule, Op.IN, "admin");


//        ResultHandler<AuthGroup> handler = context.query(AuthGroup.class, conn, statement);
//
//        System.out.println(handler.getAll());
        
    }
    
    
    @Test
    public void insertTest() {
        
        AuthGroup group1 = new AuthGroup();
        
        group1.setModule("admin");
        group1.setType(1);
        group1.setTitle("vIP1");
        group1.setDescription("");
        group1.setStatus(1);
        group1.setRules(",338,340,341,344,10000");
        
        
        group1.insert(context);
        
        context.saveChanges(conn);
        
    }
    
    
    @Test
    public void updateTest() {
        
        QueryObject<AuthGroup> queryObject = new QueryObject<>(AuthGroup.class)
                .where(AuthGroup::getId, Op.EQUAL, 8)
                .statement();
        
        AuthGroup group1 = context.<AuthGroup>query(queryObject, conn).first().get();
        
        
        group1.setTitle("VIP4");
        
        
        group1.update(context);
        
        context.saveChanges(conn);
        
        System.out.println(group1.toString());
        
    }
    
    
    @Test
    public void deleteTest() {
        AuthGroup group1 = new AuthGroup();
        group1.setId(8);
        group1.delete(context);
        context.saveChanges(conn);
        System.out.println(group1.toString());
        
    }
    
}

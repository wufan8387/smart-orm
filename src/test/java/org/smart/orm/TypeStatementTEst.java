package org.smart.orm;

import org.junit.Before;
import org.junit.Test;
import org.smart.orm.entities.light.AuthGroup;
import org.smart.orm.entities.light.AuthGroupAccess;
import org.smart.orm.execution.*;
import org.smart.orm.jdbc.ParameterTypeHandler;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.type.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TypeStatementTest {
    
    private OperationContext context;
    
    @Before
    public void before() throws SQLException, ClassNotFoundException {
        
        
        SimpleExecutor executor = new SimpleExecutor();
        executor.setParameterTypeHandler(new ParameterTypeHandler());
        context = new OperationContext();
        context.setExecutor(executor);
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connStr = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(connStr, "root", "11301130");
        executor.setConnection(conn);
    }
    
    @Test
    public void includeTest() {
        QueryObject<AuthGroup> statement = new QueryObject<>(AuthGroup.class);
        statement
                .select(AuthGroup::getId)
                .from(AuthGroup.class)
                .statement()
                .where(AuthGroup::getId, Op.EQUAL, 1);
        
        statement
                .include(context, AuthGroup.class, AuthGroupAccess.class, AuthGroup::getAccessList);
        
        statement.load();
        
        
        statement.<AuthGroup>getResult().all().forEach(t -> {
            System.out.println(t);
            t.getAccessList().forEach(System.out::println);
        });
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void queryTest() {
        
        QueryObject<AuthGroup> statement = AuthGroup
                .query(AuthGroup.class)
                .where(AuthGroup::getModule, Op.IN, "admin")
                .statement();
        
        
        statement.execute(context.getExecutor());
        ResultData<AuthGroup> result = statement.getResult();
        System.out.println(result.all());
        
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
        
        context.saveChanges();
        
    }
    
    
    @Test
    public void updateTest() {
        
        QueryObject<AuthGroup> queryObject = new QueryObject<>(AuthGroup.class)
                .where(AuthGroup::getId, Op.EQUAL, 8)
                .statement();
        
        queryObject.execute(context.getExecutor());
        queryObject.<AuthGroup>getResult().first().ifPresent(group1 -> {
            group1.setTitle("VIP4");
            
            
            group1.update(context);
            
            context.saveChanges();
            
            System.out.println(group1.toString());
        });
        
        
    }
    
    
    @Test
    public void deleteTest() {
        AuthGroup group1 = new AuthGroup();
        group1.setId(8);
        group1.delete(context);
        context.saveChanges();
        System.out.println(group1.toString());
        
    }
    
}

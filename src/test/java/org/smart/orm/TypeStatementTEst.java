package org.smart.orm;

import org.junit.Test;
import org.smart.orm.execution.*;
import org.smart.orm.operations.Op;
import org.smart.orm.operations.type.QueryObject;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

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

    @Test
    public void modelTEst() {


        QueryObject statement = new QueryObject();
        statement
                .from(Account.class)
                .select(Account::getId)
                .join(Order.class)
                .on(Account::getId, Op.EQUAL, Order::getUid)
                .statement()
                .where(Order::getUid, Op.EQUAL, 100);

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mayflower?useSSL=false&serverTimezone=UTC",
                "root", "11301130");

        Executor executor = new SimpleExecutor();
        ResultHandler<Account> handler = new ModelHandler<>(Account.class);
        handler.addListener(data -> {

        });
        executor.executeQuery(conn, handler, statement.toString(), statement.getParams().toArray());


    }


}

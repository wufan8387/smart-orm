package org.smart.orm;

import org.junit.Test;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    
    @Table("test")
    private static class TestEntity extends Model<TestEntity> {
        
        @Column
        private int id;
        
        @Column
        private String name;
    
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    }
    
    @Test
    public void selectTest() {
        
        
        Type type = TestEntity.class.getGenericSuperclass();
        
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type claz = pType.getActualTypeArguments()[0];
            String x = claz.toString();
        }
        
        
//        OperationContext context = new OperationContext();
//        FromNode<Account> fromOperation = new FromNode<>(UUID.randomUUID(), context, "test");
//
//        fromOperation
//                .select(Account::getId,"pid")
//                .where(new EqualExpression<>(Account::getId, 100));
//
//        context.query(fromOperation.getBatch());
        // select(Account.class);
        //
        // OperationContext<Account> context;
        // context.select("","").from(Account.class);
    }
    
}

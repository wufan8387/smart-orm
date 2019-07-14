package org.smart.orm;

import org.junit.Test;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;
import org.smart.orm.operations.type.EqualOperation;
import org.smart.orm.operations.type.FromOperation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

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
        
        
        OperationContext context = new OperationContext();
        FromOperation<TestEntity> fromOperation = new FromOperation<>(UUID.randomUUID(), context, "test");
        
        fromOperation
                .select(TestEntity::getId,"pid")
                .where(new EqualOperation<>(TestEntity::getId, 100));
        
        context.query(fromOperation.getBatch());
        // select(TestEntity.class);
        //
        // OperationContext<TestEntity> context;
        // context.select("","").from(TestEntity.class);
    }
    
}

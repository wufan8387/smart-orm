package org.smart.orm;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.*;
import com.querydsl.sql.dml.SQLInsertClause;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.smart.orm.annotations.Column;
import org.smart.orm.annotations.Table;
import org.smart.orm.operations.EqualOperation;
import org.smart.orm.operations.FromOperation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Types;
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
        
        fromOperation.select(t -> t.id).alias("id", "pid").where(new EqualOperation<TestEntity>("id", 100));
        
        context.query(fromOperation.getBatch());
        // select(TestEntity.class);
        //
        // OperationContext<TestEntity> context;
        // context.select("","").from(TestEntity.class);
    }
    
}

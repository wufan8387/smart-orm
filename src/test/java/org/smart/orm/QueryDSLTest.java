package org.smart.orm;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class QueryDSLTest {
    
    
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
        
        QSurvey survey = QSurvey.survey;

        SQLQuery<?> query = new SQLQuery<TestEntity>(HSQLDBTemplates.builder().newLineToSingleSpace().build());
        query
                .select(survey.name, survey.name2)
                .from(survey)
                .from(survey)
                .where(survey.name2.eq("100")).toString();
        
        
        
    
        
        
        
    }
    
    
    public static class QSurvey extends RelationalPathBase<QSurvey> {
        
        private static final long serialVersionUID = -7427577079709192842L;
        
        public static final QSurvey survey = new QSurvey("SURVEY");
        
        public final StringPath name = createString("name");
        
        public final StringPath name2 = createString("name2");
        
        public final NumberPath<Integer> id = createNumber("id", Integer.class);
        
        public final PrimaryKey<QSurvey> idKey = createPrimaryKey(id);
        
        public QSurvey(String path) {
            super(QSurvey.class, PathMetadataFactory.forVariable(path), "PUBLIC", "SURVEY");
            addMetadata();
        }
        
        public QSurvey(PathMetadata metadata) {
            super(QSurvey.class, metadata, "PUBLIC", "SURVEY");
            addMetadata();
        }
        
        protected void addMetadata() {
            addMetadata(name, ColumnMetadata.named("NAME").ofType(Types.VARCHAR));
            addMetadata(name2, ColumnMetadata.named("NAME2").ofType(Types.VARCHAR));
            addMetadata(id, ColumnMetadata.named("ID").ofType(Types.INTEGER));
        }
        
    }
    
    
    
    
    
}

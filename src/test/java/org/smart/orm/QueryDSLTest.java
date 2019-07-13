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
        
//        QSurvey survey = QSurvey.survey;
//
//        SQLQuery<?> query = new SQLQuery<AppTest.TestEntity>(HSQLDBTemplates.builder().newLineToSingleSpace().build());
//        query.select(survey.name, survey.name2).from(survey).where(survey.name2.eq("100")).toString();
        
        
    
        
        
        
    }
    
    

    
}

package org.smart.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smart.orm.dao.AuthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {
    
    public static void main(String[] args) {
        
        SpringApplication.run(JpaTest.class, args);
    }
    
    @Autowired
    private AuthDao dao;
    
    @Test
    public void findAllTest() {
        dao.findAll();
    }
    
}

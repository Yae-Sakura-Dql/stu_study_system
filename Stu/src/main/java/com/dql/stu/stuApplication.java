package com.dql.stu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Dql
 */
@SpringBootApplication
@EnableTransactionManagement
public class stuApplication {

    public static void main(String[] args) {
        SpringApplication.run(stuApplication.class, args);
    }

}

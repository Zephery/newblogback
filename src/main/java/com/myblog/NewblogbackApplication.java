package com.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.myblog.dao")
@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
//扫描组件 @ComponentScan(value = "com.spriboot.controller") 配置扫描组件的路径
public class NewblogbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewblogbackApplication.class, args);
    }
}

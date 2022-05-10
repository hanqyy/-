package com.example.webhr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
@MapperScan(basePackages = "com.example.webhr.mapper")
public class WebhrApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhrApplication.class, args);
    }

}

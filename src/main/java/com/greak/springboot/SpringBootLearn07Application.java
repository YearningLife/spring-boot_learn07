package com.greak.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.greak.springboot.mapper")
@SpringBootApplication
public class SpringBootLearn07Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearn07Application.class, args);
    }

}

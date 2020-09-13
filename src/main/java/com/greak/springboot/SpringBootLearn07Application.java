package com.greak.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 *
 * @description: TODO springboot主配置类的启动器
 * @author: zero
 * @date: 2020/9/13
 * @version: 1.0
 * 添加注解：
 *  获取cache配置类： org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.CacheConfigurationImportSelector
 *  @EnableCaching 开启缓存注解
 */
@MapperScan("com.greak.springboot.mapper")
@EnableCaching
@SpringBootApplication
public class SpringBootLearn07Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearn07Application.class, args);
    }

}

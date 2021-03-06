package com.greak.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @description: TODO springboot主配置类的启动器
 * @author: zero
 * @date: 2020/9/13
 * @version: 1.0
 * 添加注解：
 *  获取cache配置类： org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.CacheConfigurationImportSelector
 *  @EnableCaching 开启缓存注解
 *
 *  @description: TODO redis使用方式，参考链接： https://juejin.im/post/6844903858645237773
 *                      1. pom文件中添加  <artifactId>spring-boot-starter-data-redis</artifactId>
 *                      2. application.properties 文件中添加 spring.redis.host=192.168.10.6 及其他属性
 *                      3. 调用 StringRedisTemplate 和 redisTemplate 方法
 * @author: zero
 * @date: 2020/9/15
 * @version: 1.0
 */

@MapperScan("com.greak.springboot.mapper")
@EnableRabbit //启用rabbit mq注解
@EnableCaching//启用redis 注解
@EnableScheduling//启用定时任务
@SpringBootApplication
public class SpringBootLearn07Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearn07Application.class, args);
    }

}

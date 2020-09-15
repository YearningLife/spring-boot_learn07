package com.greak.springboot.config;

import com.greak.springboot.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @description: TODO 自定义redis的config信息
 * @author: zero
 * @date: 2020/9/15 22:32
 * @version: 1.0
 */
@Configuration
public class CustomizeRedisConfig {
    /*
     *
     * @description: TODO 设置默认序列化，有助于redis中实现可视化的json数据，需要指出泛型： Employee
     *                参考： RedisAutoConfiguration 中的配置信息 redisTemplate 方法
     *
     * @param: redisConnectionFactory
     * @return: org.springframework.data.redis.core.RedisTemplate<java.lang.Object,com.greak.springboot.bean.Employee>
     * @author zero
     * @date: 2020/9/15 22:39
     */
    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化信息
        Jackson2JsonRedisSerializer<Employee> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
}

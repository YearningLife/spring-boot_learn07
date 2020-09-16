package com.greak.springboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greak.springboot.bean.Employee;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.Arrays;

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
     * @description: TODO 自定义KeyGenerator，service层面调用，实现数据
     *                参考链接： https://cloud.tencent.com/developer/article/1467287
     * @param: null
     * @return:
     * @author zero
     * @date: 2020/9/16 21:17
     */

    @Bean("oneKeyGenerator")
    public KeyGenerator getKeyGenerator(){
        return  new KeyGenerator() {

            @Override
            public Object generate(Object o, Method method, Object... objects) {

                return "KeyGenerator:"+method.getName();

            }
        };
    }


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
    public RedisTemplate<Object, Object> custRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化信息
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

    /*
     *
     * @description: TODO 实现缓存数据json化
     *                参考链接： https://blog.csdn.net/sixteen_/article/details/104876420
     * @param: factory
     * @return: org.springframework.cache.CacheManager
     * @author zero
     * @date: 2020/9/16 21:01
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        return  RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }


}

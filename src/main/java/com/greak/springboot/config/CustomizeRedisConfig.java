package com.greak.springboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greak.springboot.bean.Department;
import com.greak.springboot.bean.Employee;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sun.rmi.runtime.Log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

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
            public Object generate(Object target, Method method, Object... params) {

                // return "KeyGenerator:"+method.getName();
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(",Method:");
                sb.append(method.getName());
                sb.append(",Params[");
                for (int i = 0; i < params.length; i++) {
                    sb.append(params[i].toString());
                    if (i != (params.length - 1)) {
                        sb.append(",");
                    }
                }
                sb.append("]");
                // Log.debug("Data Caching Redis Key : {}", sb.toString());
                return sb.toString();

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
    public RedisTemplate<Object, Employee> custRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化信息
        Jackson2JsonRedisSerializer<Employee> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisTemplate<Object, Department> depRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Department> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化信息
        Jackson2JsonRedisSerializer<Department> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Department>(Department.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
    /*
     *
     * @description: TODO 实现缓存数据json化
     *                参考链接： https://blog.csdn.net/sixteen_/article/details/104876420
     *                 过程较为复杂，未理解，暂不考虑
     * @param: factory
     * @return: org.springframework.cache.CacheManager
     * @author zero
     * @date: 2020/9/16 21:01
     */
    /*
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
    */

    /*
     *
     * @description: TODO 自定义实现cachemanager
     *                参考链接： https://blog.51cto.com/14819639/2502952
     *                @Primary 当存在多个cachemanager时，需要指定一个默认值
     * @param: employeeRedisTemplate
     * @return: org.springframework.cache.CacheManager
     * @author zero
     * @date: 2020/9/17 23:28
     */
    @Primary
    @Bean
    public CacheManager empCacheManage(RedisTemplate<Object, Employee> employeeRedisTemplate){

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(employeeRedisTemplate.getConnectionFactory()));

        Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<>(Employee.class);

        RedisSerializationContext<Employee, Employee> serializationContext = RedisSerializationContext.fromSerializer(serializer);

        RedisCacheConfiguration redisCacheConfiguration =  RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializationContext.getValueSerializationPair());

        return new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
    }

    @Bean
    public CacheManager depCacheManage(RedisTemplate<Object, Department> depRedisTemplate){

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(depRedisTemplate.getConnectionFactory()));

        Jackson2JsonRedisSerializer<Department> serializer = new Jackson2JsonRedisSerializer<>(Department.class);

        RedisSerializationContext<Department, Department> serializationContext = RedisSerializationContext.fromSerializer(serializer);

        RedisCacheConfiguration redisCacheConfiguration =  RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializationContext.getValueSerializationPair());

        return new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
    }

}

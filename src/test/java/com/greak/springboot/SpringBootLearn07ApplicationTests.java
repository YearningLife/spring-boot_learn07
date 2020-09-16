package com.greak.springboot;

import com.greak.springboot.bean.Employee;

import com.greak.springboot.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootLearn07ApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;//键值对都是string

    @Autowired
    RedisTemplate redisTemplate;//键值对都是对象

    @Autowired
    RedisTemplate<Object, Object> custRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void getEmpInfo(){
        Employee employee = employeeMapper.selectEmpById(1);
        System.out.println("测试类中打印"+employee.toString());
    }
    /*
     *
     * @description: TODO
     * @param: 无
     * @return: void
     * @author zero
     * @date: 2020/9/15 21:59
     * 目的：
     *      测试redis基本数据类型： String（字符串）、List（列表）、Hash（字典）、Set（集合）、Sorted Set（有序集合）
     */
    @Test
    void testRedis(){
        // stringRedisTemplate.opsForValue().append("msg","请重新输入");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println("打印redis数据："+msg);
    }

    /*
     *
     * @description: TODO redis查询数据为序列化数字，看不懂，获取到的结果也是null
     * @param: 无
     * @return: void
     * @author zero
     * @date: 2020/9/15 22:26
     */
    @Test
    void testRedisEmp(){
        Employee employee = employeeMapper.selectEmpById(1);
        //step1 .想redis中插入一个set集合
        // redisTemplate.opsForValue().set("emp_01",employee);
        //step2 .获取该结果
        String emp_01 = stringRedisTemplate.opsForValue().get("emp_01");
        //step3 .发现为null
        System.out.println("打印redis数据："+emp_01);


        //System.out.println("测试类中打印"+employee.toString());

    }
    /*
     *
     * @description: TODO 自定义配置 empRedisTemplate，实现redis的json默认，便于使用
     * @param:
     * @return: void
     * @author zero
     * @date: 2020/9/15 22:49
     */
    @Test
    void testRedisCustomer(){
        Employee employee = employeeMapper.selectEmpById(2);
        //step1 .想redis中插入一个set集合
        custRedisTemplate.opsForValue().set("emp_03",employee);
        //step2 .获取该结果
        String emp_02 = stringRedisTemplate.opsForValue().get("emp_03");
        //step3 .发现为null
        System.out.println("打印redis数据："+emp_02);
    }
}

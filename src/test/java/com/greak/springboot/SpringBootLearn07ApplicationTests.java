package com.greak.springboot;

import com.greak.springboot.bean.Book;
import com.greak.springboot.bean.Employee;
import com.greak.springboot.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    JavaMailSenderImpl javaMailSender;

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
    /*
     *
     * @description: TODO 测试rabbitmq消息发送
     *                相关简介： 参考 CustomizeRabbitMqConfig 中注释内容
     *                单播模式，点对点消息队列
     * @param: null
     * @return:
     * @author zero
     * @date: 2020/9/20 18:45
     */

    @Test
    public void sendRabbitMq(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第2个 Rabbit消息 ");
        map.put("sms", Arrays.asList("短信发送",123,true));
        //第一种方式，最后一个参数只能为message，所以需要转化
        //Message message = rabbitTemplate.getMessageConverter().toMessage(map, new MessageProperties());
        //rabbitTemplate.send("exchange.direct","starbucks.news", message);
        //第二种方式，最后一个参数为为object，所以直接为map就可以
        rabbitTemplate.convertAndSend("exchange.direct","starbucks.news", map);
    }
    /*
     *
     * @description: TODO 测试rabbitmq中消息接收
     *                相关简介： 参考 CustomizeRabbitMqConfig 中注释内容
     *                单播模式，点对点消息队列
     * @param:
     * @return: void
     * @author zero
     * @date: 2020/9/20 19:00
     */
    @Test
    public void receiveRabbitMq(){
        System.out.println("测试mq配置消息");
        Object o = rabbitTemplate.receiveAndConvert("starbucks.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /*
     * @referLink：
     * @description: TODO  测试广播模式
     *                与 exchange.fanout 绑定的 所有 mq 队列都可以收到消息，所以 第二个参数：Routing key  不会起到作用
     * @param:
     * @return: void
     * @author zero
     * @date: 2020/9/20 19:31
     */
    @Test
    public void sendBookMq(){
        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦","曹雪芹"));
    }

    /*
     * @referLink：
     * @description: TODO 接收广播模式的消息队列，该方法是否正确，需要验证
     * @param:
     * @return: void
     * @author zero
     * @date: 2020/9/20 19:42
     */
    @Test
    public void receiveBookMq(){
        System.out.println("接收到广播模式fanout的消息队列");
        //Book book = (Book) rabbitTemplate.receiveAndConvert("starbucks.news");
        //System.out.println(book.toString());
    }
    /*
     * @referLink： 视频： https://www.bilibili.com/video/BV1Et411Y7tQ?p=90
     *             方法链接： https://blog.csdn.net/level_Tiller/article/details/103401716
     * @description: TODO amqpAdmin 为 RabbitAutoConfiguration 中的方法
     *                1. 创建exchange、queue、已经将两者绑定起来
     * @param:
     * @return: void
     * @author zero
     * @date: 2020/9/21 21:59
     */
    @Test
    public void createMq(){
        amqpAdmin.declareExchange(new FanoutExchange("amqpAdmin.fanout"));//创建exchange
        amqpAdmin.declareQueue(new Queue("amqpAdmin.users"));
        amqpAdmin.declareBinding(new Binding("amqpAdmin.users",DestinationType.QUEUE,"amqpAdmin.fanout","amqpAdmin.emp" ,null));//将exchange与queue绑定在一起
    }
    /**
     * @referLink：
     * @description: TODO 测试邮件发送，发送失败，待核查
     *                  日志中报送：535 Login fail. Authorization code is expired
     * @author: zero
     * @date: 2020/11/10
     * @version: 1.0
     */
    @Test
    public void sendMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("测试-邮件发送");

        message.setText("明天去吃饭");

        javaMailSender.send(message);
    }
}

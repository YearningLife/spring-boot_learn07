package com.greak.springboot.service;

import com.greak.springboot.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @description: TODO 测试rabbitmq消息队列的监听
 *                  1. @EnableRabbit 开启注解，在 启动器： SpringBootLearn07Application 上面添加此注解
 *                  2. @RabbitListener 作为监听器使用
 *                  3. 注解相关解释在 org.springframework.amqp.rabbit.annotation 中
 * @author: zero
 * @date: 2020/9/21 20:44
 * @version: 1.0
 */
@Service
public class BookRabbitMqService {

    /*
     * @referLink：
     * @description: TODO rabbitmq的监听器返回指定数据
     *                book 为监听器反序列话后的数据
     * @param: book
     * @return: void
     * @author zero
     * @date: 2020/9/21 21:03
     */
    @RabbitListener(queues="starbucks")
    public void receiveMq(Book book){
        System.out.println("读取监听器中的内容："+book);
    }

    /*
     * @referLink：
     * @description: TODO 返回的数据没有指定格式，一步步解析
     * @param: message
     * @return: void
     * @author zero
     * @date: 2020/9/21 21:07
     */
    @RabbitListener(queues={"starbucks.news","starbucks"})
    public void receiveMessageMq(Message message){
        System.out.println("获取消息体："+message.getBody());
        System.out.println("获取消息内容："+message.getMessageProperties());
    }
}

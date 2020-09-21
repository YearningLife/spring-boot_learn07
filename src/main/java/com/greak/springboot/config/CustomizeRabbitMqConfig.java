package com.greak.springboot.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @referLink： 视频参考链接： https://www.bilibili.com/video/BV1Et411Y7tQ?p=88
 * @description: TODO  自定义rabbitmq配置类  （参考 RabbitAutoConfiguration 类）
 *                    存储在mq消息转化为json消息，可分为以下步骤
 *                    1.  在 pom文件中引入  <artifactId>spring-boot-starter-amqp</artifactId>
 *                    2.  宿主服务器引入： docker pull rabbitmq:management
 *                                 启动： docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management
 *                    3. 在properties 中引入 （参考： RabbitProperties.class）
 *                                  spring.rabbitmq.addresses=192.168.10.6
 *                                  spring.rabbitmq.username=guest
 *                                  spring.rabbitmq.password=guest
 *                     4. 测试类中发送和接受信息方式：（参考链接： https://potoyang.gitbook.io/spring-in-action-v5/di-8-zhang-fa-song-yi-bu-xiao-xi/8.2-shi-yong-rabbitmq-he-amqp/untitled-1）
 *                              rabbitTemplate.convertAndSend
 *                              rabbitTemplate.receiveAndConvert
 *                     5. 自定义方法message擦converter方法，参考 RabbitTemplateConfigurer rabbitTemplateConfigurer 中的
 *                          configurer.setMessageConverter((MessageConverter)messageConverter.getIfUnique());
 *                     6. 开启注解，参考 BookRabbitMqService 中的解释
 * @author: zero
 * @date: 2020/9/20 18:32
 * @version: 1.0
 */
@Configuration
public class CustomizeRabbitMqConfig {



    /*
     * @referLink：
     * @description: TODO 自定义message 转换为json方式
     * @param:
     * @return: org.springframework.amqp.support.converter.MessageConverter
     * @author zero
     * @date: 2020/9/20 19:09
     */
    @Bean
    public MessageConverter jsonMessageConvert(){

        return new Jackson2JsonMessageConverter();
    }
}

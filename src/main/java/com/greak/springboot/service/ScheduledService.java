package com.greak.springboot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @description: TODO 这是一个定时任务的demo
 * @author: zero
 * @date: 2020/11/10 21:15
 * @version: 1.0
 */
@Service
public class ScheduledService {

    /**
     * @referLink： https://www.bilibili.com/video/BV1Et411Y7tQ?p=96
     *              测试定时任务举例： https://blog.csdn.net/Qiwan2/article/details/89848298
     * @description: TODO 定时任务注解：  需要两个注解1.@cheduled  2。@EnableScheduling
     * @author: zero
     * @date: 2020/11/10
     * @version: 1.0
     */
    /*
     * <ul>
     * <li>second</li>
     * <li>minute</li>
     * <li>hour</li>
     * <li>day of month</li>
     * <li>month</li>
     * <li>day of week</li>
     * </ul>
     */
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void hello(){
        //System.out.println("hello.....");
        System.out.println("测试定时任务。。。。。");
    }
}

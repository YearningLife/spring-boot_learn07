package com.greak.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @description: TODO spring security 相关依赖
 *                  参考链接一： https://www.bilibili.com/video/BV1Et411Y7tQ?p=99
 *                  参考链接二： https://spring.io/guides/gs/securing-web/
 * @Step: 步骤一： 添加依赖
 *         步骤二： 创建config配置类类
 *         步骤三： 配置登陆权限
 * @author: zero
 * @date: 2020/11/15 22:24
 * @version: 1.0
 */
@Configuration
@EnableWebSecurity
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @referLink：
     * @description: TODO 配置访问权限
     * @author: zero
     * @date: 2020/11/15
     * @version: 1.0
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().antMatchers("/").permitAll().
                antMatchers("/level1/**").hasRole("VIP1").
                antMatchers("/level2/**").hasRole("VIP2").
                antMatchers("/level3/**").hasRole("VIP3");

        http.formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("wangsisi").password("123456").roles("VIP1","VIP2","VIP3");
    }
}

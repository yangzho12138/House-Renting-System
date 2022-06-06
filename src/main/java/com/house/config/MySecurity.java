package com.house.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @version SpringSecurity 用户权限分离配置类
 * @since 2022/5/21
 **/
@EnableWebSecurity
public class MySecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许跨域，配置后 SpringSecurity 会自动寻找 name = corsConfigurationSource 的 Bean
        http.cors();
        //关闭 CSRF 防御
        http.csrf().disable();
    }
}

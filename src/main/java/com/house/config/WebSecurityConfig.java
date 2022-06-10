package com.house.config;

import com.house.component.MyAccessDeniedHandler;
import com.house.component.MyAuthenticationEntryPoint;
import com.house.component.MyOncePerRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @version SpringSecurity 用户权限分离配置类
 * @since 2022/5/21
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyOncePerRequestFilter myOncePerRequestFilter;

    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    private final MyAccessDeniedHandler myAccessDeniedHandler;

    public WebSecurityConfig(MyOncePerRequestFilter myOncePerRequestFilter, MyAuthenticationEntryPoint myAuthenticationEntryPoint, MyAccessDeniedHandler myAccessDeniedHandler) {
        this.myOncePerRequestFilter = myOncePerRequestFilter;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许跨域，配置后 SpringSecurity 会自动寻找 name = corsConfigurationSource 的 Bean
        http.cors();
        //关闭 CSRF 防御
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/register", "/user/login", "/user/check_login").permitAll()
                .antMatchers("/admin/*").hasAnyRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);
        //异常处理
        http.exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler);
    }
}

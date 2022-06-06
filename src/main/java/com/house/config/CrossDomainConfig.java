package com.house.config;

import com.google.common.collect.ImmutableList;
import com.house.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * @version 跨域配置类
 * @since 2022/5/21
 **/
@Configuration
public class CrossDomainConfig {

    final
    JwtUtil jwtUtil;

    public CrossDomainConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        //允许客户端携带认证信息
        configuration.setAllowCredentials(true);
        //允许所有域名可以跨域访问
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        //允许使用 GET,POST,PUT,DELETE 方法访问
        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE", "UPDATE"));
        //允许服务端访问客户端请求头
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        //暴露哪些头部信息，为完成认证，需要能访问令牌头部信息
        configuration.setExposedHeaders(Collections.singletonList(jwtUtil.getHeader()));
        //注册跨域配置
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
}

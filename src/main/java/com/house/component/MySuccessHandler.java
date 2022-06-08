package com.house.component;

import com.house.dto.AuthUser;
import com.house.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 登录成功的处理类
 * @since 2022/5/22
 **/
@Component
public class MySuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.header}")
    private String header;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String token = JwtUtil.createJWT(authUser.getPhone());
        httpServletResponse.setHeader(header, token);
    }
}

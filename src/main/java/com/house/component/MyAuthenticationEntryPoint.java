package com.house.component;

import com.house.common.JSONAuthentication;
import com.house.common.Result;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint extends JSONAuthentication implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result respBean;

        //打印错误
        logger.error(String.valueOf(authException));

        if (authException instanceof AccountExpiredException) {
            //账号过期
            throw new OperationException(ExceptionEnum.USER_ACCOUNT_EXPIRED);
        } else if (authException instanceof InternalAuthenticationServiceException) {
            //用户不存在
            throw new OperationException(ExceptionEnum.USER_ACCOUNT_NOT_EXIST);
        } else if (authException instanceof BadCredentialsException) {
            //用户名或密码错误（也就是用户名匹配不上密码）
            throw new OperationException(ExceptionEnum.USER_NOT_FOUND_OR_PASSWORD_WRONG);
        } else if (authException instanceof CredentialsExpiredException) {
            //密码过期
            throw new OperationException(ExceptionEnum.USER_ACCOUNT_PASSWORD_EXPIRED);
        } else if (authException instanceof DisabledException) {
            //账号不可用
            throw new OperationException(ExceptionEnum.USER_ACCOUNT_DISABLE);
        } else if (authException instanceof LockedException) {
            //账号锁定
            throw new OperationException(ExceptionEnum.USER_ACCOUNT_LOCKED);
        } else {
            throw new OperationException(ExceptionEnum.USER_NOT_LOGIN);
        }
    }
}

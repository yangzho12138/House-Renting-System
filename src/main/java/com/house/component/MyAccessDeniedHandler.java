package com.house.component;

import com.house.common.JSONAuthentication;
import com.house.common.Result;
import com.house.common.StatusCode;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler extends JSONAuthentication implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.error(ExceptionEnum.USER_NO_PERMISSIONS.getMessage(), StatusCode.ACCESS_ERROR);
        WriteJSON(request, response, result);
    }
}

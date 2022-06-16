package com.house.controller;

import com.house.common.Result;
import com.house.dto.LoginUser;
import com.house.service.LoginService;
import com.house.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @version 用户登录接口
 * @since 2022/5/23
 */
@RestController
@CrossOrigin
public class LoginController {

    @Value("${jwt.header}")
    private String header;

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/doLogin")
    public Result doLogin(HttpServletResponse response, @RequestBody LoginUser loginUser){
        Result result = loginService.doLogin(loginUser);
        Map<String, String> tokenMap = (Map<String, String>) result.getData();
        response.setHeader(header, tokenMap.get("token"));
        return loginService.doLogin(loginUser);
    }

    @RequestMapping("/doLogout")
    public Result doLogout(){
        return loginService.doLogout();
    }
}


package com.house.controller;

import com.house.common.Result;
import com.house.dto.LoginUser;
import com.house.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 用户登录接口
 * @since 2022/5/23
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/doLogin")
    public Result doLogin(@RequestBody LoginUser loginUser){
        return loginService.doLogin(loginUser);
    }

    @RequestMapping("/doLogout")
    public Result doLogout(){
        return loginService.doLogout();
    }
}


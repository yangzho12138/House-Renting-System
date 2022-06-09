package com.house.controller;

import com.house.common.Result;
import com.house.dto.LoginUser;
import com.house.service.LoginService;
import org.springframework.web.bind.annotation.*;

/**
 * @version 用户登录接口
 * @since 2022/5/23
 */
@RestController
@CrossOrigin
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


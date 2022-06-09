package com.house.service;

import com.house.common.Result;
import com.house.dto.LoginUser;
import com.house.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    Result doLogin(LoginUser loginUser);

    Result doLogout();

    void register(User user);
}

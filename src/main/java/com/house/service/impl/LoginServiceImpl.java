package com.house.service.impl;

import com.alibaba.fastjson.JSON;
import com.house.common.Constant;
import com.house.common.Result;
import com.house.component.BCryptPasswordEncoderUtil;
import com.house.component.LoginAuthProvider;
import com.house.component.RedisCache;
import com.house.dto.AuthUser;
import com.house.dto.LoginUser;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.User;
import com.house.service.LoginService;
import com.house.service.UserService;
import com.house.utils.ConvertUtil;
import com.house.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version 登录服务业务层
 * @since 2022/5/22
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${jwt.header}")
    private String tokenHead;

    private final UserService userService;

    private final BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    private final LoginAuthProvider loginAuthProvider;

    private final UserDetailsService userDetailsService;

    private final RedisCache redisCache;

    private final JwtUtil jwtUtil;

    public LoginServiceImpl(UserService userService, LoginAuthProvider loginAuthProvider, UserDetailsService userDetailsService, RedisCache redisCache, JwtUtil jwtUtil, BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil) {
        this.userService = userService;
        this.loginAuthProvider = loginAuthProvider;
        this.userDetailsService = userDetailsService;
        this.redisCache = redisCache;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoderUtil = bCryptPasswordEncoderUtil;
    }

    @Override
    public Result doLogin(LoginUser loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        Authentication authentication = loginAuthProvider.authenticate(authenticationToken);
        if (Objects.isNull(authentication)){
            throw new OperationException(ExceptionEnum.USER_NOT_FOUND_OR_PASSWORD_WRONG);
        }
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String phone = authUser.getPhone();
        String token = JwtUtil.createJWT(phone);

        redisCache.setCacheObject(Constant.REDIS_TOKEN_PREFIX + phone, token);
        redisCache.setCacheObject(Constant.REDIS_USER_INFO_PREFIX + phone, authUser);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return Result.success("登录成功", map);
    }

    @Override
    public Result doLogout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String phone = authUser.getPhone();

        redisCache.deleteObject(Constant.REDIS_TOKEN_PREFIX + phone);
        redisCache.deleteObject(Constant.REDIS_USER_INFO_PREFIX + phone);
        SecurityContextHolder.clearContext();

        return Result.success("登出成功");
    }

    @Override
    @Transactional
    public Result register(User user) {
        String oldPassword = user.getPassword();
        String newPassword = bCryptPasswordEncoderUtil.encode(oldPassword);
        user.setPassword(newPassword);
        userService.addUser(user);
        user.setPassword(oldPassword);
        Result result = doLogin(ConvertUtil.convert(user));
        result.setMessage("注册成功，自动登录");
        return result;
    }
}

package com.house.service.impl;

import com.house.common.Result;
import com.house.component.BCryptPasswordEncoderUtil;
import com.house.component.RedisCache;
import com.house.dto.AuthUser;
import com.house.dto.LoginUser;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.service.LoginService;
import com.house.service.UserService;
import com.house.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version 登录服务业务层
 * @since 2022/5/22
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final UserService userService;

    private final BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final RedisCache redisCache;

    private final JwtUtil jwtUtil;

    public LoginServiceImpl(UserService userService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, RedisCache redisCache, JwtUtil jwtUtil, BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.redisCache = redisCache;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoderUtil = bCryptPasswordEncoderUtil;
    }

    @Override
    public Result doLogin(LoginUser loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication)){
            throw new OperationException(ExceptionEnum.USER_NOT_FOUND_OR_PASSWORD_WRONG);
        }
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String phone = authUser.getPhone();
        String token = JwtUtil.createJWT(phone);

        redisCache.setCacheObject("Token_" + phone, token);
        redisCache.setCacheObject("UserDetails_" + phone, authUser);

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

        redisCache.deleteObject("Token_" + phone);
        redisCache.deleteObject("UserDetails_" + phone);
        SecurityContextHolder.clearContext();

        return Result.success("登出成功");
    }
}

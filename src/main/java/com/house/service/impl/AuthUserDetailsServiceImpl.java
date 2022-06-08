package com.house.service.impl;

import com.house.dto.AuthUser;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.User;
import com.house.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 实现 Security 提供的 UserDetailsService 接口
 * @since 2022/5/22
 **/
@Service(value = "userDetailsService")
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthUserDetailsServiceImpl.class);

    private final UserService userService;

    public AuthUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserByPhone(s);
        if (user == null){
            throw new OperationException(ExceptionEnum.USER_ACCOUNT_NOT_EXIST);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getType().split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        logger.info("AuthUserDetailsServiceImpl-loadUserByUsername......user ===> " + user);
        return new AuthUser(user, authorities);
    }
}

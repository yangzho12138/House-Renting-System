package com.house.utils;

import com.house.common.Constant;
import com.house.component.RedisCache;
import com.house.dto.AuthUser;
import com.house.enums.ExceptionEnum;
import com.house.pojo.User;
import com.house.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @version 从全局的认证信息中获取目前登录的 User 相应信息
 * @since 2022/5/26
 **/
@Component
public class UserUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

    private final RedisCache redisCache;

    public UserUtil(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 通过上下文的 Authentication 获得目前 User 登录的 Phone 信息
     * 再通过 Redis 获取目前的用户信息
     **/
    public User getUserInfo(){
        String phone =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser authUser = redisCache.getCacheObject(Constant.REDIS_USER_INFO_PREFIX + phone);
        return authUser.getUser();
    }
}

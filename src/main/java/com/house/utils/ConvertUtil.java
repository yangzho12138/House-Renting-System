package com.house.utils;

import com.house.dto.LoginUser;
import com.house.pojo.User;

/**
 * @version DTO 转换工具类
 * @since 2022/5/27
 **/
public class ConvertUtil {

    public static LoginUser convert(User user){
        return new LoginUser(user.getPhone(), user.getPassword());
    }
}

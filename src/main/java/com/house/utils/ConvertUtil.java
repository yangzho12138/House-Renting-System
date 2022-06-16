package com.house.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.house.dto.LoginUser;
import com.house.enums.UserStatusEnum;
import com.house.pojo.User;
import com.house.vo.UserVO;
import org.springframework.beans.BeanUtils;

/**
 * @version DTO 转换工具类
 * @since 2022/5/27
 **/
public class ConvertUtil {

    public static LoginUser convert(User user){
        return new LoginUser(user.getPhone(), user.getPassword());
    }

    public static UserVO convertToVo(User user){
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setPhone(user.getPhone());
        userVO.setUsername(user.getUsername());
        userVO.setStatus(UserStatusEnum.of(user.getStatus()));
        userVO.setInfo(user.getInfo());
        return userVO;
    }
}

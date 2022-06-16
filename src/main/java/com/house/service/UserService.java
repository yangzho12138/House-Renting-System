package com.house.service;

import com.house.common.Page;
import com.house.pojo.User;
import com.house.vo.PasswordVO;
import com.house.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface UserService {
    User getUserByPhone(String phone);

    Page<UserVO> findUserByPage(Map<String, Object> params);

    void updatePassword(PasswordVO passwordVO);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);

    void deleteUserForce(Integer userId);
}

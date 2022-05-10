package com.house.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.house.dao.UserDao;
import com.house.dao.UserListDao;
import com.house.dto.UserExecution;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.exception.UserOperationException;
import com.house.pojo.User;
import com.house.pojo.UserList;
import com.house.vo.PasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User login(String username, String password) {
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("password", password);
		List<User> users = userDao.select(params);
		return users.get(0);
	}
	
	public List<User> findUserListByCondition(Map<String, Object> params) {
		return userDao.select(params);
	}

	public void updatePassword(PasswordVO passwordVO){
		if(passwordVO == null){
			throw new OperationException(ExceptionEnum.Parameter_Null_Exception);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("userId", passwordVO.getUserId());
		params.put("password", passwordVO.getOldPassword());
		List<User> users = userDao.select(params);
		if(users.size() < 1){
			throw new OperationException(ExceptionEnum.USER_NOT_FOUND_OR_PASSWORD_WRONG);
		}
		User user = users.get(0);
		user.setPassword(passwordVO.getNewPassword());
		try{
			int effectedNum = userDao.update(user);
			if(effectedNum < 1){
				throw new OperationException(ExceptionEnum.Database_Operation_Exception);
			}
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.Database_Connection_Exception);
		}
	}

	@Transactional
	public UserExecution addUserListAndUserAccount(User user) {
		if(user == null){
			throw new OperationException(ExceptionEnum.Parameter_Null_Exception);
		}
		User user = new User();
		user.setUsername(userList.getPhone());
		user.setPassword(userList.getPhone());
		user.setType(userList.getType());
		try{
			int effectedNum = userDao.insertUser(user);
			if(effectedNum < 1){
				return new UserExecution(false,"添加用户帐号失败");
			}
		}catch (Exception e){
			throw new UserOperationException(e.toString());
		}
		int accountId = user.getId();
		userList.setUserId(accountId);
		try{
			int effectedNum = userListDao.insertUserList(userList);
			if(effectedNum < 1){
				return new UserExecution(false,"添加用户信息失败");
			}
		}catch (Exception e){
			throw new UserOperationException(e.toString());
		}
		return new UserExecution(true);
	}


	public UserExecution updateUserList(UserList userList) {
        if(userList == null){
            return new UserExecution(false,"更新用户信息为空");
        }

        try{
            int effectedNum = userListDao.updateUserList(userList);
            if(effectedNum < 1){
                return new UserExecution(false,"更新用户信息失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        return new UserExecution(true);
	}

    @Transactional
    public UserExecution deleteUser(Integer userListId) {
        if(userListId == null){
            return new UserExecution(false,"删除用户信息Id为空");
        }
        UserList userList = userListDao.findUserListById(userListId);
        Integer userAccountId = userList.getUserId();
        try{
            int effectedNum = userDao.deleteUser(userAccountId);
            if(effectedNum < 1){
                return new UserExecution(false,"删除用户帐号失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }

        try{
            int effectedNum = userListDao.deleteUserList(userListId);
            if(effectedNum < 1){
                return new UserExecution(false,"删除用户信息失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        return new UserExecution(true);
    }


}

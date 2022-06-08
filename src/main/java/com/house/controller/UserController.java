package com.house.controller;

import java.util.HashMap;
import java.util.Map;

import com.house.dto.LoginUser;
import com.house.common.Result;
import com.house.pojo.User;
import com.house.service.LoginService;
import com.house.service.UserService;
import com.house.validate.UserInsertValidate;
import com.house.validate.UserUpdateValidate;
import com.house.vo.PasswordVO;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/register")
	public Result register(@Validated({UserInsertValidate.class}) @RequestBody User user){
		userService.register(user);
		return Result.success("用户 " + user.getUsername() + " 注册成功！");
	}

	@RequestMapping(value = "/select",method = RequestMethod.GET)
	public Result getUerListByCondition(@RequestParam Map<String, Object> params){
		return Result.success("按条件查找用户列表成功",
				userService.findUserByPage(params));
	}

	@RequestMapping(value="/add",method = RequestMethod.POST)
	public Result addUser(@Validated({UserInsertValidate.class}) @RequestBody User user){
		userService.addUser(user);
		return Result.success("添加用户成功");
	}

	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public Result updateUser(@Validated({UserUpdateValidate.class}) @RequestBody User user){
		userService.updateUser(user);
		return Result.success("更新用户成功");
	}

	@RequestMapping(value="/delete/{userId}",method = RequestMethod.DELETE)
	public Result deleteUser(@PathVariable("userId")Integer userId){
		userService.deleteUser(userId);
		return Result.success("删除用户成功");
	}

	@RequestMapping(value="/editPassword",method = RequestMethod.PUT)
	public Result updateUser(@Validated @RequestBody PasswordVO passwordVO){
		if (StringUtils.isEmpty(passwordVO.getPhoneNumber()) &&
		   passwordVO.getUserId() == null){
			return Result.error("用户登录账号 ID 和电话号码为空");
		}
		userService.updatePassword(passwordVO);
		return Result.success("修改密码成功");
	}


}

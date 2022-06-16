package com.house.controller;

import java.util.Map;
import com.house.common.Result;
import com.house.pojo.User;
import com.house.service.LoginService;
import com.house.service.UserService;
import com.house.utils.UserUtil;
import com.house.validate.UserInsertValidate;
import com.house.validate.UserUpdateValidate;
import com.house.vo.PasswordVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {

	private final UserService userService;

	private final LoginService loginService;

	public UserController(UserService userService, LoginService loginService) {
		this.userService = userService;
		this.loginService = loginService;
	}

	@PostMapping(value = "/register")
	public Result register(@Validated({UserInsertValidate.class}) @RequestBody User user){
		user.setType("USER");
		return loginService.register(user);
	}

	@RequestMapping(value = "/select",method = RequestMethod.GET)
	public Result getUerListByCondition(@RequestParam Map<String, Object> params){
		return Result.success("按条件查找用户列表成功",
				userService.findUserByPage(params));
	}

	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public Result updateUser(@Validated({UserUpdateValidate.class}) @RequestBody User user){
		//此接口不接收密码修改和用户状态修改
		user.setPassword(null);
		user.setStatus(null);
		user.setType(null);
		userService.updateUser(user);
		return Result.success("更新用户成功");
	}

	@RequestMapping(value="/editPassword",method = RequestMethod.PUT)
	public Result updateUser(@Validated @RequestBody PasswordVO passwordVO){
		userService.updatePassword(passwordVO);
		return Result.success("修改密码成功");
	}

	@RequestMapping(value="/delete",method = RequestMethod.DELETE)
	public Result deleteUser(){
		Integer userId = UserUtil.getUserInfo().getId();
		userService.deleteUser(userId);
		loginService.doLogout();
		return Result.success("删除用户成功");
	}

	@DeleteMapping(value = "/deleteForce")
	public Result deleteUserForce(){
		Integer userId = UserUtil.getUserInfo().getId();
		userService.deleteUserForce(userId);
		loginService.doLogout();
		return Result.success("成功删除用户");
	}

}

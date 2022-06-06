package com.house.pojo;

import com.house.enums.UserStatusEnum;
import com.house.enums.UserTypeEnum;
import com.house.validate.UserInsertValidate;
import com.house.validate.UserUpdateValidate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @version 用户信息实体类
 * @since 2022/5/9
 **/
public class User {
	/**
	 * 用户 ID
	 **/
	@NotNull(message = "用户 ID 不为空", groups = {UserUpdateValidate.class})
	private Integer id;

	/**
	 * 用户名称
	 **/
	@NotBlank(message = "用户名称不为空", groups = {UserInsertValidate.class})
	private String username;

	/**
	 * 用户电话号码
	 **/
	@NotBlank(message = "用户电话号码不为空", groups = {UserInsertValidate.class})
	private String phone;

	/**
	 * 用户登录密码
	 **/
	@NotBlank(message = "用户登录密码不为空", groups = {UserInsertValidate.class})
	private String password;

	/**
	 * 用户类型
	 **/
	private String type = "2";

	/**
	 * 用户状态
	 **/
	private Integer status = UserStatusEnum.ENABLE.getCode();

	/**
	 * 其他信息
	 **/
	private String info;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}

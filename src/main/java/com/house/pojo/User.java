package com.house.pojo;

/**
 * @version 用户信息实体类
 * @since 2022/5/9
 **/
public class User {
	/**
	 * 用户 ID
	 **/
	private Integer id;

	/**
	 * 用户名称
	 **/
	private String username;

	/**
	 * 用户电话号码
	 **/
	private String phone;

	/**
	 * 用户登录密码
	 **/
	private String password;

	/**
	 * 用户类型
	 **/
	private Integer type;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}

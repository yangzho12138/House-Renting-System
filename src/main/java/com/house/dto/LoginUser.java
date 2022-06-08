package com.house.dto;

import javax.validation.constraints.NotBlank;

public class LoginUser {

    @NotBlank(message = "登录账号 ID 不为空")
    private String username;

    @NotBlank(message = "登录密码不为空")
    private String password;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

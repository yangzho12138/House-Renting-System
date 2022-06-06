package com.house.dto;

import javax.validation.constraints.NotBlank;

public class LoginUser {

    @NotBlank(message = "登录账号 ID 不为空")
    private String account;

    @NotBlank(message = "登录密码不为空")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

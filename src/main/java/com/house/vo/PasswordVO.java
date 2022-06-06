package com.house.vo;

import javax.validation.constraints.NotBlank;

public class PasswordVO {

    private Integer userId;

    private String phoneNumber;

    @NotBlank(message = "用户旧密码不为空")
    private String oldPassword;

    @NotBlank(message = "用户新密码不为空")
    private String newPassword;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

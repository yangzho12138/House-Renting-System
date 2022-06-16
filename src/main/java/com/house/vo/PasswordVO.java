package com.house.vo;

import javax.validation.constraints.NotBlank;

public class PasswordVO {

    @NotBlank(message = "用户电话号码不为空")
    private String phone;

    @NotBlank(message = "用户旧密码不为空")
    private String oldPassword;

    @NotBlank(message = "用户新密码不为空")
    private String newPassword;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

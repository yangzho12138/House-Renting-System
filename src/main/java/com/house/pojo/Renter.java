package com.house.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @version 租户信息实体类
 * @since 2022/5/9
 **/
public class Renter {
    /**
     * ID
     **/
    private Integer id;

    /**
     * 租户 ID
     **/
    private Integer renterId;

    /**
     * 租户住址
     **/
    private String address;

    /**
     * 租户电话号码
     **/
    private String phone;

    /**
     * 租户身份证号码
     **/
    @NotBlank(message = "租户身份证号码不允许为空")
    private String cardId;

    /**
     * 租户电子邮箱
     **/
    @NotBlank(message = "租户电子邮箱不允许为空")
    @Email(message = "租户电子邮箱格式不合法")
    private String email;

    /**
     * 租户出生年月
     **/
    private String birth;

    /**
     * 租户性别，0 表示女，1 表示男
     **/
    private Integer sex;

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

    public Integer getRenterId() {
        return renterId;
    }

    public void setRenterId(Integer renterId) {
        this.renterId = renterId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

package com.house.vo;

/**
 * @version 用户展示实体类
 * @since 2022/6/15
 **/
public class UserVO {
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
     * 用户状态
     **/
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

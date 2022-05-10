package com.house.pojo;

import java.math.BigDecimal;

/**
 * @version : 房屋信息实体类
 * @since 2022/5/9
 **/
public class House {
    /**
     * 房屋 ID
     **/
    private Integer id;

    /**
     * 房屋地址
     **/
    private String address;

    /**
     * 房屋类型
     **/
    private Integer type;

    /**
     * 房屋最大允许容纳房客数
     **/
    private Integer maxUsers;

    /**
     * 房主 ID
     **/
    private Integer ownerId;

    /**
     * 房屋租金（月记）
     **/
    private BigDecimal price;

    /**
     * 房屋状态
     **/
    private Integer status;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

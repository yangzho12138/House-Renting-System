package com.house.pojo;

import com.house.enums.OwnerStatusEnum;
import javax.validation.constraints.NotBlank;

/**
 * @version 房主信息实体类
 * @since 2022/5/9
 **/
public class Owner {
    /**
     * ID
     **/
    private Integer id;

    /**
     * 房主 ID
     **/
    private Integer ownerId;

    /**
     * 房主名称
     **/
    private String name;

    /**
     * 房主居住地址
     **/
    private String address;

    /**
     * 房主身份证号码
     **/
    @NotBlank(message = "房主身份证号码不为空")
    private String cardId;

    /**
     * 房主电话号码
     **/
    private String phone;

    /**
     * 房主是否已经注销
     **/
    //TODO 普通用户不允许修改房主状态，只有管理员允许修改房东状态
    private Integer status = OwnerStatusEnum.Alive.getCode();

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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

package com.house.pojo;

import com.house.enums.HouseStatusEnum;
import com.house.enums.HouseTypeEnum;
import com.house.validate.EnumValid;
import com.house.validate.HouseInsertValidate;
import com.house.validate.HouseUpdateValidate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @version : 房屋信息实体类
 * @since 2022/5/9
 **/
public class House {
    /**
     * 房屋 ID
     **/
    @NotNull(message = "房屋 ID 不为空", groups = {HouseUpdateValidate.class})
    private Integer id;

    /**
     * 房屋地址
     **/
    @NotBlank(message = "房屋地址信息不为空", groups = {HouseInsertValidate.class})
    private String address;

    /**
     * 房屋类型
     **/
    @NotNull(message = "房屋类型信息不为空", groups = {HouseInsertValidate.class})
    @EnumValid(message = "房屋类型不符合要求", target = HouseTypeEnum.class, groups = {HouseInsertValidate.class})
    private Integer type;

    /**
     * 房屋最大允许容纳房客数
     **/
    @NotNull(message = "房屋最大允许容纳房客数信息不为空", groups = {HouseInsertValidate.class})
    @Min(value = 1, message = "房屋最大允许容纳房客数至少为 1 人", groups = {HouseInsertValidate.class})
    private Integer maxUsers;

    /**
     * 房主 ID
     **/
    @NotNull(message = "房主 ID 不为空", groups = {HouseInsertValidate.class})
    private Integer ownerId;

    /**
     * 房屋租金（月记）
     **/
    @NotNull(message = "房屋租金不为空", groups = {HouseInsertValidate.class})
    private BigDecimal price;

    /**
     * 房屋状态
     **/
    @NotNull(message = "房屋状态信息不为空", groups = {HouseInsertValidate.class})
    @EnumValid(message = "房屋状态信息不符合要求", target = HouseStatusEnum.class, groups = {HouseInsertValidate.class})
    private Integer status = HouseStatusEnum.Not_Rented.getCode();

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

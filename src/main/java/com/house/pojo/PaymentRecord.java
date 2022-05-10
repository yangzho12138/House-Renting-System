package com.house.pojo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @version 缴费记录信息实体类
 * @since 2022/5/9
 **/
public class PaymentRecord {
    /**
     * 缴费记录 ID
     **/
    private Integer id;

    /**
     * 房屋 ID
     **/
    private Integer houseId;

    /**
     * 房主 ID
     **/
    private Integer ownerId;

    /**
     * 租户 ID
     **/
    private Integer renterId;

    /**
     * 房屋租赁费用
     **/
    private BigDecimal housePrice;

    /**
     * 平台手续费用
     **/
    private BigDecimal platformPrice;

    /**
     * 缴费日期
     **/
    private Date payDate;

    /**
     * 缴费状态
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

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getRenterId() {
        return renterId;
    }

    public void setRenterId(Integer renterId) {
        this.renterId = renterId;
    }

    public BigDecimal getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(BigDecimal housePrice) {
        this.housePrice = housePrice;
    }

    public BigDecimal getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(BigDecimal platformPrice) {
        this.platformPrice = platformPrice;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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

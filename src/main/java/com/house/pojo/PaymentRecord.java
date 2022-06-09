package com.house.pojo;

import com.house.enums.PaymentStatusEnum;
import com.house.validate.EnumValid;
import com.house.validate.PaymentRecordInsertValidate;
import com.house.validate.PaymentRecordUpdateValidate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
    @NotNull(message = "缴费记录 ID 不为空", groups = {PaymentRecordUpdateValidate.class})
    private Integer id;

    /**
     * 房屋 ID
     **/
    @NotNull(message = "房屋 ID 不为空", groups = {PaymentRecordInsertValidate.class})
    private Integer houseId;

    /**
     * 房主 ID
     **/
    @NotNull(message = "房主 ID 不为空", groups = {PaymentRecordInsertValidate.class})
    private Integer ownerId;

    /**
     * 租户 ID
     **/
    @NotNull(message = "租户 ID 不为空", groups = {PaymentRecordInsertValidate.class})
    private Integer renterId;

    /**
     * 房屋租赁费用
     **/
    @NotNull(message = "房屋租赁费用不为空", groups = {PaymentRecordInsertValidate.class})
    private BigDecimal housePrice;

    /**
     * 平台手续费用
     **/
    private BigDecimal platformPrice = BigDecimal.TEN;

    /**
     * 缴费日期
     **/
    private Date payDate;

    /**
     * 缴费状态
     **/
    private Integer status = PaymentStatusEnum.Owe.getCode();

    /**
     * 其他信息
     **/
    private String info;

    public PaymentRecord(@NotNull(message = "缴费记录 ID 不为空", groups = {PaymentRecordUpdateValidate.class}) Integer id, @NotNull(message = "房屋 ID 不为空", groups = {PaymentRecordInsertValidate.class}) Integer houseId, @NotNull(message = "房主 ID 不为空", groups = {PaymentRecordInsertValidate.class}) Integer ownerId, @NotNull(message = "租户 ID 不为空", groups = {PaymentRecordInsertValidate.class}) Integer renterId, @NotNull(message = "房屋租赁费用不为空", groups = {PaymentRecordInsertValidate.class}) BigDecimal housePrice, BigDecimal platformPrice, Date payDate, Integer status, String info) {
        this.id = id;
        this.houseId = houseId;
        this.ownerId = ownerId;
        this.renterId = renterId;
        this.housePrice = housePrice;
        this.platformPrice = platformPrice;
        this.payDate = payDate;
        this.status = status;
        this.info = info;
    }

    public PaymentRecord() {
    }

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

package com.house.pojo;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @version 看房记录实体类
 * @since 2022/6/6
 **/
public class HouseView {

    /**
     * 看房记录 ID
     **/

    private Integer id;

    /**
     * 房屋 ID
     **/
    @NotNull(message = "房屋id不为空")
    private Integer houseId;

    /**
     * 租户 ID
     **/
    @NotNull(message = "租户id不为空")
    private Integer renterId;

    /**
     * 看房日期
     **/
    private Date viewDate;

    /**
     * 看房评价星级
     **/
    private Integer star;

    /**
     * 看房详细评价
     **/
    private String detail;

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

    public Integer getRenterId() {
        return renterId;
    }

    public void setRenterId(Integer renterId) {
        this.renterId = renterId;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

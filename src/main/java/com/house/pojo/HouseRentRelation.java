package com.house.pojo;

/**
 * @version 房屋租赁关系实体类
 * @since 2022/5/9
 **/
public class HouseRentRelation {
    /**
     * 房屋租赁关系 ID
     **/
    private Integer id;

    /**
     * 房屋 ID
     **/
    private Integer houseId;

    /**
     * 房屋租户 ID
     **/
    private Integer renterId;

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
}

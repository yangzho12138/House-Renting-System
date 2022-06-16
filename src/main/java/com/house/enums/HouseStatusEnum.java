package com.house.enums;

public enum  HouseStatusEnum {

    Not_Rented(0, "未出租"),
    Rented(1, "已出租"),
    Out_Date(2, "租期到期且未缴费"),
    Sealed(3, "已被封禁");

    private final Integer code;

    private final String status;

    private HouseStatusEnum(Integer code, String status){
        this.code = code;
        this.status = status;
    }

    public String of(Integer code){
        for (HouseStatusEnum houseStatusEnum : HouseStatusEnum.values()){
            if (houseStatusEnum.getCode().equals(code)){
                return houseStatusEnum.getStatus();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}

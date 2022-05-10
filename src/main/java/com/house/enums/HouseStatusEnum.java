package com.house.enums;

public enum  HouseStatusEnum {

    Not_Rented(0, "未出租"),
    Rented(1, "已出租满"),
    Out_Date(2, "已过期");

    private final Integer code;

    private final String status;

    private HouseStatusEnum(Integer code, String status){
        this.code = code;
        this.status = status;
    }

    public String of(Integer code){
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}

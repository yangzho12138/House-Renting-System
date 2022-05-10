package com.house.enums;

public enum OwnerStatusEnum {
    Alive(0, "未注销"),
    Withdraw(1, "已注销");

    private final Integer code;

    private final String status;

    private OwnerStatusEnum(Integer code, String status){
        this.code = code;
        this.status = status;
    }

    public String of(Integer code){
        for (OwnerStatusEnum value : OwnerStatusEnum.values()) {
            if (value.code.equals(code)){
                return value.status;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }
}

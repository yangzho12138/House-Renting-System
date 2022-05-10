package com.house.enums;

public enum PaymentStatusEnum {

    Paid(0, "已经缴费"),
    Owe(1, "欠费");

    private final Integer code;

    private final String status;

    private PaymentStatusEnum(Integer code, String status){
        this.code = code;
        this.status = status;
    }

    public String of(Integer code){
        for (PaymentStatusEnum value : PaymentStatusEnum.values()) {
            if (value.code.equals(code)){
                return value.status;
            }
        }
        return null;
    }

}

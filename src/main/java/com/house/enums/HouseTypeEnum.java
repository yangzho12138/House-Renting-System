package com.house.enums;

public enum HouseTypeEnum {

    Bungalow(1, "平房"),
    Building_With_Balcony(2, "带阳台的楼房"),
    Villa(3, "独立式住宅");

    private final Integer code;

    private final String type;

    private HouseTypeEnum(Integer code, String type){
        this.code = code;
        this.type = type;
    }

    public String of(Integer code){
        for (HouseTypeEnum value : HouseTypeEnum.values()) {
            if (value.code.equals(code)){
                return value.type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}

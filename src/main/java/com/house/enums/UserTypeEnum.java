package com.house.enums;

public enum UserTypeEnum {

    Admin(0, "最高权限管理员"),
    Manager(1, "管理员"),
    User(2, "普通用户");

    private final Integer code;

    private final String type;

    private UserTypeEnum(Integer code, String type){
        this.code = code;
        this.type = type;
    }

    public String of(Integer code){
        for (UserTypeEnum value : UserTypeEnum.values()) {
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

package com.house.enums;

/**
 * @version 用户状态枚举
 * @since 2022/5/21
 **/
public enum UserStatusEnum {

    ENABLE(1, "正常"),
    LOCKED(2, "封控"),
    EXPIRED(3, "过期"),
    ;

    private final Integer code;

    private final String status;

    private UserStatusEnum(Integer code, String status){
        this.code = code;
        this.status = status;
    }

    public String of(Integer code){
        for (UserStatusEnum value : UserStatusEnum.values()) {
            if (value.code.equals(code)){
                return value.status;
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

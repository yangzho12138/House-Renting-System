package com.house.enums;

/**
 * @version 错误枚举类型
 * @since 2022/5/9
 **/
public enum ExceptionEnum {
    PARAMETER_NULL_EXCEPTION(10001, "输入参数为空"),
    DATABASE_OPERATION_EXCEPTION(10002, "数据库操作异常"),
    DATABASE_CONNECTION_EXCEPTION(10003, "数据库访问连接异常"),
    USER_NOT_FOUND_OR_PASSWORD_WRONG(10004, "用户不存在或密码错误"),
    OWNER_HOUSE_RENTED(10005, "名下房产还处租期内，无法删除用户"),
    RENTED_HOUSE_NOT_OUT_DATE(10006, "租取房产还处租期内，无法删除");

    private final Integer code;

    private final String message;

    private ExceptionEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public ExceptionEnum of(Integer code){
        for (ExceptionEnum value : ExceptionEnum.values()) {
            if (value.code.equals(code)){
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

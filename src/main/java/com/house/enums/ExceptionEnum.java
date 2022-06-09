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
    RENTED_HOUSE_NOT_OUT_DATE(10006, "租取房产还处租期内，无法删除"),
    USER_ACCOUNT_LOCKED(10007, "用户账户已禁用，请联系管理员"),
    PASSWORD_WRONG_EXCEPTION(10008, "密码错误，请重新输入"),
    USER_ACCOUNT_NOT_EXIST(10009, "用户名（电话号码不存在）"),
    USER_ACCOUNT_EXPIRED(10010, "账号已过期"),
    USER_ACCOUNT_PASSWORD_EXPIRED(10011, "密码过期"),
    USER_ACCOUNT_DISABLE(10012, "账号不可用"),
    USER_NOT_LOGIN(10013, "用户未登录成功"),
    USER_NO_PERMISSIONS(10014, "用户权限不足"),
    REDIS_OPERATE_ERROR(10015, "Redis 缓存运行异常"),
    USER_NOT_REGISTER_AS_RENTER(10016, "用户未注册为租户，请补充填写租户信息"),
    HOUSE_RENTED(10017, "房屋已被出租，请重新选房"),
    PAYMENT_RECORD_PAYED(10018, "该订单已支付，请勿重复操作");

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

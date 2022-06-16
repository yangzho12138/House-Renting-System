package com.house.common;

/**
 * Created by treasure.zhou on 2019/3/21.
 */
public class StatusCode {

    public static final int SUCCESS = 200;    //成功
    public static final int ERROR = 500;      //失败
    public static final int USER_LOGIN_ERROR = 501; //用户登录失败
    public static final int PARAMETER_ERROR = 400; //输入参数错误
    public static final int LOGIN_ERROR = 401; //用户名或密码错误
    public static final int ACCESS_ERROR = 402;//权限不足
    public static final int REMOTE_ERROR = 403;//远程调用失败
    public static final int REPEAT_ERROR = 405;   //重复操作
}

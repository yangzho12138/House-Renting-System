package com.house.common;

/**
 * Created by treasure.zhou on 2022/5/8
 */
public class Result {

    private boolean flag;   //是否成功
    private Integer code;   //返回码
    private String message; //返回信息
    private Object data;    //返回数据

    public Result() {
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(String message, Integer code, Object data){
        return new Result(true, code, message, data);
    }

    public static Result success(String message, Object data){
        return new Result(true, StatusCode.SUCCESS, message, data);
    }

    public static Result success(String message){
        return new Result(true, StatusCode.SUCCESS, message, null);
    }

    public static Result error(String message, Integer code, Object data){
        return new Result(false, StatusCode.ERROR, message, data);
    }

    public static Result error(String message, Object data){
        return new Result(false, StatusCode.ERROR, message, data);
    }

    public static Result error(String message){
        return new Result(false, StatusCode.ERROR, message, null);
    }

    public boolean isFlag() {

        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

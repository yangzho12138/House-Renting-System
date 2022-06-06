package com.house.exception;

import com.house.enums.ExceptionEnum;

/**
 * @ClassName OperationException
 * @Description 项目运行时抛出错误
 * @Date 2022/5/9 21:43
 * @Version 1.0
 **/
public class OperationException extends RuntimeException {
    private static final long serialVersionUID = 2365905813758097384L;

    private ExceptionEnum exceptionType;

    private String exceptionReason;

    public OperationException(ExceptionEnum exceptionType){
        this.exceptionType = exceptionType;
    }

    public OperationException(ExceptionEnum exceptionType, String exceptionReason){
        this.exceptionType = exceptionType;
        this.exceptionReason = exceptionReason;
    }

    public ExceptionEnum getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionEnum exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }
}

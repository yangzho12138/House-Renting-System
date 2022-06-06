package com.house.config;

import com.house.common.Result;
import com.house.common.StatusCode;

import com.house.exception.OperationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @version 异常处理机制
 * @since 2022/5/9
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = OperationException.class)
    public Result exception(OperationException e){
        e.printStackTrace();
        return Result.error(e.getExceptionType().getMessage());
    }
}

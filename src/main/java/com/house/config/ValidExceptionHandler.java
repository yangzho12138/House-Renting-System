package com.house.config;

import com.house.common.Result;
import com.house.common.StatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 统一校验异常处理的处理类
 * @since 2022/5/20
 **/
@RestControllerAdvice(basePackages = "com.house.controller")
public class ValidExceptionHandler {

    //数据校验异常的统一处理及返回
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handleValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            errorMap.put(item.getField(), item.getDefaultMessage());
        });
        return Result.error("数据校验异常", StatusCode.PARAMETER_ERROR, errorMap);
    }
}

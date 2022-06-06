package com.house.validate;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @since 枚举值校验器
 * @version 2022/5/20
 **/
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {

    /**
     * 枚举需要校验的属性名
     **/
    private static final String DEFAULT_FIELD = "code";

    /**
     * 枚举类
     **/
    private Class<?> clazz;

    /**
     * 比较的属性
     **/
    private String field;


    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.clazz = constraintAnnotation.target();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        if (!clazz.isEnum()) {
            return false;
        }
        Object[] enumOptions = clazz.getEnumConstants();
        try{
            Method method;
            if (DEFAULT_FIELD.equals(field)){
                method = clazz.getMethod(field);
            } else {
                String getMethodName = "get" + upperCaseChar(this.field);
                method = clazz.getMethod(getMethodName);
            }

            for (Object obj : enumOptions){
                Object code = method.invoke(obj);
                if (value.toString().equals(code.toString())){
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    private static String upperCaseChar(String str) {
        return str.toUpperCase();
    }
}

package com.house.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 自定义校验注解：是否为对应类的枚举值
 * @since 2022/5/20
 **/
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> target();

    String field() default "code";
}

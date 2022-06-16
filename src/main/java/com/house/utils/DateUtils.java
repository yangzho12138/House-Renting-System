package com.house.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.house.common.Constant;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;

import java.sql.Date;

/**
 * @version 日期转换工具类
 * @since 2022/6/14
 **/
public class DateUtils {

    public static Date convert(Object value){
        String valueStr = (String) value;
        try{
            DateTime dateTime = DateUtil.parse(valueStr, Constant.DATE_FORMAT_STR);
            return new Date(dateTime.getTime());
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATE_FORMAT_WRONG, "日期格式请规定为 yyyy-MM-dd");
        }

    }
}

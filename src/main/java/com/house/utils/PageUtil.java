package com.house.utils;

import java.util.Map;

/**
 * @version 分页工具类
 * @since 2022/5/10
 **/
public class PageUtil {

    private static final String CurrPage_Key = "currPage";

    private static final String PageSize_Key = "pageSize";

    /**
     * 默认从第 1 页开始查询数据
     **/
    private static final Integer Default_CurrPage = 1;

    /**
     * 默认每页 10 行数据
     **/
    private static final Integer Default_PageSize = 10;

    public static Map<String, Object> addPageParams(Map<String, Object> params){
        params.computeIfAbsent(CurrPage_Key, k -> Default_CurrPage);
        params.computeIfAbsent(PageSize_Key, k -> Default_PageSize);
        return params;
    }
}

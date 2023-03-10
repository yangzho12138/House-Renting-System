package com.house.common;

import java.math.BigDecimal;

public class Constant {

    public static final BigDecimal DEFAULT_PLATFORM_PRICE = BigDecimal.TEN;

    public static final String REDIS_TOKEN_PREFIX = "Token_";

    public static final String REDIS_USER_INFO_PREFIX = "UserDetails_";

    public static final BigDecimal DEFAULT_PLATFORM_LIQUIDATED_DAMAGES = new BigDecimal("0.2");

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";


}

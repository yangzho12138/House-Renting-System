package com.house.service.impl;

import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.Renter;
import com.house.pojo.User;
import com.house.service.RentService;
import com.house.service.RenterService;
import com.house.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 处理租房的相关操作
 * @since 2022/5/26
 **/
@Service
public class RentServiceImpl implements RentService {

    private final UserUtil userUtil;

    private final RenterService renterService;

    public RentServiceImpl(UserUtil userUtil, RenterService renterService) {
        this.userUtil = userUtil;
        this.renterService = renterService;
    }


    @Override
    @Transactional
    public void rent(Integer houseId) {
        //1. 通过 Authentication 认证拿取目前的用户(租户)相关信息
        User userInfo = userUtil.getUserInfo();
        // 判断该用户是否租赁过房源，未租赁过房源返回错误，请用户完善租户信息
        Renter renter = renterService.getRenterByRenterId(userInfo.getId());
        if (renter == null){
            throw new OperationException(ExceptionEnum.USER_NOT_REGISTER_AS_RENTER);
        }

        //2. 通过 houseId 查询对应的 House 信息，House 假如状态是已租赁，则抛出异常
        //3. 通过 houseId 查询对应的 Owner 信息
        //4. 插入 house_rent 进行房屋租赁
        //5. 插入 payment_record 计算具体的费用（平台费用从配置文件中读取）
        //6. 更新房子状态（房子已被出租）
    }
}

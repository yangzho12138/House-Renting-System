package com.house.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.house.common.Constant;
import com.house.common.Page;
import com.house.dao.HouseRentRelationDao;
import com.house.enums.ExceptionEnum;
import com.house.enums.HouseStatusEnum;
import com.house.enums.PaymentStatusEnum;
import com.house.exception.OperationException;
import com.house.pojo.*;
import com.house.service.*;
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

    private final HouseRentRelationDao houseRentRelationDao;

    private final RenterService renterService;

    private final HouseService houseService;

    private final HouseOwnerService ownerService;

    private final PaymentRecordService paymentRecordService;

    public RentServiceImpl(UserUtil userUtil, HouseRentRelationDao houseRentRelationDao, RenterService renterService, HouseService houseService, HouseOwnerService ownerService, PaymentRecordService paymentRecordService) {
        this.userUtil = userUtil;
        this.houseRentRelationDao = houseRentRelationDao;
        this.renterService = renterService;
        this.houseService = houseService;
        this.ownerService = ownerService;
        this.paymentRecordService = paymentRecordService;
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
        House house = houseService.getHouseById(houseId);
        if (HouseStatusEnum.Rented.getCode().equals(house.getStatus())){
            throw new OperationException(ExceptionEnum.HOUSE_RENTED);
        }
        //3. 通过 houseId 查询对应的 Owner 信息
        Owner owner = ownerService.getByOwnerId(house.getOwnerId());
        //4. 插入 house_rent 进行房屋租赁
        HouseRentRelation houseRentRelation = new HouseRentRelation();
        houseRentRelation.setRenterId(renter.getRenterId());
        houseRentRelation.setHouseId(houseId);
        houseRentRelationDao.insert(ImmutableList.of(houseRentRelation));
        //5. 插入 payment_record 计算具体的费用（平台费用从配置文件中读取）
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setHouseId(houseId);
        paymentRecord.setOwnerId(owner.getOwnerId());
        paymentRecord.setRenterId(renter.getRenterId());
        paymentRecord.setHousePrice(house.getPrice());
        paymentRecord.setStatus(PaymentStatusEnum.Owe.getCode());
        paymentRecord.setPlatformPrice(Constant.DEFAULT_PLATFORM_PRICE);
        paymentRecordService.addPaymentRecord(paymentRecord);
        //6. 更新房子状态（房子已被出租）
        House newHouse = new House();
        newHouse.setId(houseId);
        newHouse.setStatus(HouseStatusEnum.Rented.getCode());
        houseService.updateHouse(house);
    }

    @Override
    @Transactional
    public void forceWithdraw(Integer houseId) {
        Integer renterId = userUtil.getUserInfo().getId();
        //租户强制退租
        //1. 租户未缴费，直接删除 paymentRecord 记录
        Page<PaymentRecord> paymentRecords = paymentRecordService.findPaymentRecordListByPage(ImmutableMap.of("houseId", houseId,
                "renterId", renterId));
        PaymentRecord record = (PaymentRecord) paymentRecords.getList().get(0);
        paymentRecordService.deletePaymentRecord(record.getId());
        //2. 删除 house_rent 租房记录
        deleteHouseRentRelation(houseId, renterId);
        //3. house 状态更新为未出租
        House house = new House();
        house.setId(houseId);
        house.setStatus(HouseStatusEnum.Not_Rented.getCode());
        houseService.updateHouse(house);
    }

    public void deleteHouseRentRelation(Integer houseId, Integer renterId){
        Integer delete = houseRentRelationDao.delete(ImmutableMap.of("houseId", houseId,
                                                                        "renterId", renterId));
        if (delete < 1){
            throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION, "插入数据失败");
        }
    }
}

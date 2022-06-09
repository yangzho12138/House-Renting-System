package com.house.component;


import com.house.common.Page;
import com.house.enums.HouseStatusEnum;
import com.house.enums.PaymentStatusEnum;
import com.house.pojo.House;
import com.house.pojo.PaymentRecord;
import com.house.pojo.Renter;
import com.house.service.*;
import com.house.utils.EmailSendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.*;

@Component
public class ScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    private final PaymentRecordService paymentRecordService;

    private final RenterService renterService;

    private final HouseService houseService;

    private final RentService rentService;

    public ScheduleTask(HouseService houseService, PaymentRecordService paymentRecordService, RenterService renterService, RentService rentService) {
        this.houseService = houseService;
        this.paymentRecordService = paymentRecordService;
        this.renterService = renterService;
        this.rentService = rentService;
    }

    // 每天中午12点发送邮件通知用户缴费
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendEmail() throws MessagingException {
        Map<String, Object> params = new HashMap<>();
        java.sql.Date dateBound = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        java.sql.Date dateLimit= new java.sql.Date(calendar.getTimeInMillis());
        params.put("dateLimit",dateLimit);
        params.put("dateBound",dateBound);
        params.put("status", PaymentStatusEnum.Owe.getCode());
        Page<PaymentRecord>  p = paymentRecordService.findPaymentRecordListByPage(params);
        List<PaymentRecord> list = p.getList();
        list.forEach(paymentRecord -> {
            Renter renter = renterService.getRenterByRenterId(paymentRecord.getRenterId());
            Integer renterId = renter.getRenterId();
            try {
                EmailSendUtil.send(renter.getEmail());
            } catch (MessagingException e) {
                logger.error("发送至 " + renterId + " 租户的信息发送失败，请稍后重试! 原因是：" + e.getMessage());
            }

        });
    }

    // 每天凌晨清除3天内未付款的订单
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredRecord(){
        Map<String, Object> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-3);
        java.sql.Date dateBound= new java.sql.Date(calendar.getTimeInMillis());
        params.put("dateBound",dateBound);
        params.put("status", PaymentStatusEnum.Owe.getCode());
        Page<PaymentRecord>  p = paymentRecordService.findPaymentRecordListByPage(params);
        List<PaymentRecord> list = p.getList();
        list.forEach(paymentRecord -> {
            paymentRecordService.deletePaymentRecord(paymentRecord.getId());
            // 更新房屋信息
            House house = new House();
            house.setId(paymentRecord.getHouseId());
            house.setStatus(HouseStatusEnum.Not_Rented.getCode());
            houseService.updateHouse(house);
            // 删除租赁合同
            rentService.deleteHouseRentRelation(paymentRecord.getHouseId(), paymentRecord.getRenterId());
        });
    }

}



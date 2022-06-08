package com.house.component;


import com.house.common.Page;
import com.house.enums.HouseStatusEnum;
import com.house.enums.PaymentStatusEnum;
import com.house.pojo.House;
import com.house.pojo.HouseRentRelation;
import com.house.pojo.PaymentRecord;
import com.house.pojo.Renter;
import com.house.service.HouseService;
import com.house.service.PaymentRecordService;
import com.house.service.RenterService;
import com.house.service.UserService;
import com.house.utils.EmailSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.*;

@Component
public class ScheduleTask {

    @Autowired
    PaymentRecordService paymentRecordService;

    @Autowired
    RenterService renterService;

    @Autowired
    HouseService houseService;

    @Autowired
    UserService userService;

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
        Page  p = paymentRecordService.findPaymentRecordListByPage(params);
        List<?> list = p.getList();
        for(int i = 0; i < list.size(); i++){
            Object[] obj = (Object[]) list.get(i);
            Integer renterId = (Integer) obj[3];
            Renter renter = renterService.getRenterByRenterId(renterId);
            EmailSendUtil.send(renter.getEmail());
        }
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
        Page  p = paymentRecordService.findPaymentRecordListByPage(params);
        List<?> list = p.getList();
        List<PaymentRecord> target = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Object[] obj = (Object[]) list.get(i);
            Integer paymentId = (Integer) obj[0];
            Integer houseId = (Integer) obj[1];
            Integer renterId = (Integer) obj[3];
            // 删除交易记录
            paymentRecordService.deletePaymentRecord(paymentId);
            // 更新房屋信息
            House house = new House();
            house.setId(houseId);
            house.setStatus(HouseStatusEnum.Not_Rented.getCode());
            houseService.updateHouse(house);
            // 删除HouseRentRelation
            Map<String,Object> map = new HashMap<>();
            map.put("houseId",houseId);
            map.put("renterId",renterId);
            //userService.deleteHouseRentRelation(map);
        }

    }

}



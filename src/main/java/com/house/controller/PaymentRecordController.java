package com.house.controller;

import com.house.common.Result;
import com.house.pojo.PaymentRecord;
import com.house.service.PaymentRecordService;
import com.house.validate.PaymentRecordInsertValidate;
import com.house.validate.PaymentRecordUpdateValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value="/paymentRecord")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    public PaymentRecordController(PaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public Result getPaymentRecordListByCondition(@RequestParam Map<String, Object> params){
        return Result.success("按条件查找缴费记录成功",
                paymentRecordService.findPaymentRecordListByPage(params));
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Result addPaymentRecord(@Validated({PaymentRecordInsertValidate.class}) @RequestBody PaymentRecord PaymentRecord){
        paymentRecordService.addPaymentRecord(PaymentRecord);
        return Result.success("添加缴费信息记录成功");
    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public Result updatePaymentRecord(@Validated({PaymentRecordUpdateValidate.class}) @RequestBody PaymentRecord PaymentRecord){
        paymentRecordService.updatePaymentRecord(PaymentRecord);
        return Result.success("修改缴费信息记录成功");
    }

    @RequestMapping(value="/delete/{paymentRecordId}",method = RequestMethod.DELETE)
    public Result deletePaymentRecord(@PathVariable("paymentRecordId")Integer paymentRecordId){
        paymentRecordService.deletePaymentRecord(paymentRecordId);
        return Result.success("删除缴费信息记录成功");
    }

    @RequestMapping(value = "/pay")
    public Result payForTheHouse(@RequestParam("paymentRecordId") Integer paymentRecordId){
        if (paymentRecordId == null){
            return Result.error("房租缴费失败，请选择您需要缴费的订单进行缴纳或联系管理员手动缴费");
        }
        paymentRecordService.pay(paymentRecordId);
        return Result.success("房租缴费成功");
    }
}

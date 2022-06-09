package com.house.service;

import com.house.common.Page;
import com.house.pojo.PaymentRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface PaymentRecordService {

    Page<PaymentRecord> findPaymentRecordListByPage(Map<String, Object> params);

    void addPaymentRecord(PaymentRecord paymentRecord);

    void deletePaymentRecord(Integer paymentRecordId);

    void updatePaymentRecord(PaymentRecord paymentRecord);

    PaymentRecord findPaymentRecordById(Integer paymentRecordId);

    void pay(Integer paymentRecordId);
}

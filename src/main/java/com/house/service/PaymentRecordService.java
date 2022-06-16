package com.house.service;

import com.google.common.collect.ImmutableMap;
import com.house.common.Page;
import com.house.pojo.PaymentRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface PaymentRecordService {

    Page<PaymentRecord> findPaymentRecordListByPage(Map<String, Object> params);

    List<PaymentRecord> findPaymentRecords(Integer userId);

    void addPaymentRecord(PaymentRecord paymentRecord);

    void deletePaymentRecord(Integer paymentRecordId);

    void updatePaymentRecord(PaymentRecord paymentRecord);

    PaymentRecord findPaymentRecordById(Integer paymentRecordId);

    void pay(Integer paymentRecordId);

    List<PaymentRecord> findPaymentRecordList(Integer houseId);
}

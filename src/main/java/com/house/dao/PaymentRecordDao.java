package com.house.dao;

import com.house.pojo.PaymentRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PaymentRecordDao {

    List<PaymentRecord> select(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    Integer insert(PaymentRecord paymentRecord);

    Integer update(PaymentRecord paymentRecord);

    Integer delete(@Param("paymentRecordIds") List<Integer> paymentRecordIds);
}

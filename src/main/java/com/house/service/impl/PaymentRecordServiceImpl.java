package com.house.service.impl;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.house.common.Page;
import com.house.dao.PaymentRecordDao;
import com.house.enums.ExceptionEnum;
import com.house.enums.PaymentStatusEnum;
import com.house.exception.OperationException;
import com.house.pojo.PaymentRecord;
import com.house.service.NoticeService;
import com.house.service.PaymentRecordService;
import com.house.utils.DateUtils;
import com.house.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;


@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {

    private final PaymentRecordDao paymentRecordDao;

    private final NoticeService noticeService;

    public PaymentRecordServiceImpl(PaymentRecordDao paymentRecordDao, NoticeService noticeService) {
        this.paymentRecordDao = paymentRecordDao;
        this.noticeService = noticeService;
    }

    /**
     * 查询缴费记录
     **/
    @Override
    public Page<PaymentRecord> findPaymentRecordListByPage(Map<String, Object> params) {
        //判断参数中是否有 dateLimit 和 dateBound
        params.computeIfPresent("dateLimit", (k, v) -> {
            return DateUtils.convert(v);
        });
        params.computeIfPresent("dateBound", (k, v) -> {
            return DateUtils.convert(v);
        });
        Integer totalCount = paymentRecordDao.count(params);
        PageUtil.addPageParams(params);
        return new Page<PaymentRecord>(paymentRecordDao.select(params), totalCount,
                (Integer) params.get("pageSize"),
                (Integer) params.get("currPage"));
    }

    @Override
    public List<PaymentRecord> findPaymentRecords(Integer userId) {
        List<PaymentRecord> records = paymentRecordDao.select(ImmutableMap.of("ownerId", userId));
        records.addAll(paymentRecordDao.select(ImmutableMap.of("renterId", userId)));
        return records;
    }

    /**
     * 添加缴费记录
     **/
    @Override
    public void addPaymentRecord(PaymentRecord paymentRecord) {
        try{
            int effectedNum = paymentRecordDao.insert(paymentRecord);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    /**
     * 删除缴费记录
     **/
    @Override
    public void deletePaymentRecord(Integer paymentRecordId) {
        try{
            int effectedNum = paymentRecordDao.delete(ImmutableList.of(paymentRecordId));
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    /**
     * 更新缴费记录
     **/
    @Override
    public void updatePaymentRecord(PaymentRecord paymentRecord) {
        try{
            int effectedNum = paymentRecordDao.update(paymentRecord);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    /**
     * 根据缴费 ID 获取具体缴费记录
     **/
    @Override
    public PaymentRecord findPaymentRecordById(Integer paymentRecordId) {
        return paymentRecordDao.select(ImmutableMap.of("paymentRecordId", paymentRecordId)).get(0);
    }

    /**
     * 缴费
     **/
    @Override
    @Transactional
    public void pay(Integer paymentRecordId) {
        List<PaymentRecord> paymentRecords = paymentRecordDao.select(ImmutableMap.of("id", paymentRecordId));
        PaymentRecord oldRecord = paymentRecords.get(0);
        if (PaymentStatusEnum.Payed.getCode().equals(oldRecord.getStatus())){
            throw new OperationException(ExceptionEnum.PAYMENT_RECORD_PAYED);
        }
        PaymentRecord newRecord = new PaymentRecord();
        newRecord.setId(paymentRecordId);
        newRecord.setStatus(PaymentStatusEnum.Payed.getCode());
        newRecord.setPayDate(new Date(new java.util.Date().getTime()));
        paymentRecordDao.update(newRecord);
        Integer renterId = oldRecord.getRenterId();
        List<Integer> noticeIds = noticeService.getNoticeIdsByRenterId(renterId);
        if (!noticeIds.isEmpty()){
            noticeService.deleteByNoticeIds(noticeIds);
        }
    }

    @Override
    public List<PaymentRecord> findPaymentRecordList(Integer houseId) {
        return paymentRecordDao.select(ImmutableMap.of("houseId", houseId));
    }
}

package com.house.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.house.common.Constant;
import com.house.common.Page;
import com.house.dao.HouseDao;
import com.house.dao.HouseRentRelationDao;
import com.house.dao.HouseViewDao;
import com.house.enums.ExceptionEnum;
import com.house.enums.HouseStatusEnum;
import com.house.enums.PaymentStatusEnum;
import com.house.exception.OperationException;
import com.house.pojo.House;
import com.house.pojo.HouseView;
import com.house.pojo.PaymentRecord;
import com.house.service.HouseService;
import com.house.service.PaymentRecordService;
import com.house.utils.DateUtils;
import com.house.utils.PageUtil;
import com.house.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;

    private final HouseViewDao houseViewDao;

    private final PaymentRecordService paymentRecordService;

    private final HouseRentRelationDao houseRentRelationDao;

    public HouseServiceImpl(HouseDao houseDao, HouseViewDao houseViewDao, PaymentRecordService paymentRecordService, HouseRentRelationDao houseRentRelationDao) {
        this.houseDao = houseDao;
        this.houseViewDao = houseViewDao;
        this.paymentRecordService = paymentRecordService;
        this.houseRentRelationDao = houseRentRelationDao;
    }

    @Override
    public Page<House> findHouseListByPage(Map<String, Object> params) {
        Integer totalCount = houseDao.count(params);
        List<House> houses =  houseDao.select(PageUtil.addPageParams(params));
        return new Page<House>(houses, totalCount, (Integer) params.get("pageSize"), (Integer) params.get("currPage"));
    }

    @Override
    public Page<HouseView> findHouseViewListPage(Map<String, Object> params){
        //判断参数中是否有 viewDateLowerLimit 和 viewDateUpperLimit
        params.computeIfPresent("viewDateLowerLimit", (k, v) -> {
            return DateUtils.convert(v);
        });
        params.computeIfPresent("viewDateUpperLimit", (k, v) -> {
            return DateUtils.convert(v);
        });
        Integer totalCount = houseViewDao.count(params);
        List<HouseView> houseViews = houseViewDao.select(PageUtil.addPageParams(params));
        return new Page<HouseView>(houseViews,totalCount,(Integer) params.get("pageSize"), (Integer) params.get("currPage"));
    }

    @Override
    public List<House> findHouseListByCondition(Map<String, Object> params){
        return houseDao.select(params);
    }

    @Override
    public void addHouse(House house) {
        try{
            int effectedNum = houseDao.insert(house);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void addHouseView(HouseView houseView){
        houseView.setRenterId(UserUtil.getUserInfo().getId());
        try{
            int effectedNum = houseViewDao.insert(houseView);
            if(effectedNum < 1){
                throw  new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void deleteHouse(Integer houseId) {
        if(houseId == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        //1. 查询目前该房子的状态，是否处于出租状态且租金已经缴纳过，才需要赔偿违约金
        List<House> houseList = houseDao.select(ImmutableMap.of("id", houseId));
        House deleteHouse = houseList.get(0);
        if (HouseStatusEnum.Rented.getCode().equals(deleteHouse.getStatus())){
            //2. 计算需要支付的租金
            String price = computeLiquidatedDamages(houseId).toPlainString();
            throw new OperationException(ExceptionEnum.DELETE_HOUSE_FORCE, "请缴纳总计 " + price + " 元的违约金");
        }
        try{
            List<Integer> houseIds = new ArrayList<>();
            houseIds.add(houseId);
            int effectedNum = houseDao.delete(houseIds);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void updateHouse(House house) {
        if(house == null || house.getId() == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        try{
            int effectedNum = houseDao.update(house);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }


    @Override
    public House getHouseById(Integer houseId) {
        List<House> houses = houseDao.select(ImmutableMap.of("id", houseId));
        return houses.get(0);
    }

    @Override
    public void deleteHouses(List<Integer> houseIds) {
        try{
            houseDao.delete(houseIds);
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, "删除房产信息失败");
        }
    }

    @Transactional
    @Override
    public void deleteHouseForce(Integer houseId) {
        List<PaymentRecord> paymentRecordList = paymentRecordService.findPaymentRecordList(houseId);
        paymentRecordList.forEach(paymentRecord -> {
            Integer renterId = paymentRecord.getRenterId();
            Integer delete = houseRentRelationDao.delete(ImmutableMap.of("houseId", houseId,
                                                                            "renterId", renterId));
            if (delete < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION, "删除 houseId为：" + houseId + " renterId为：" + renterId + "的租赁关系失败");
            }
            paymentRecordService.deletePaymentRecord(paymentRecord.getId());
        });
        deleteHouses(ImmutableList.of(houseId));
    }

    private BigDecimal computeLiquidatedDamages(Integer houseId){
       List<PaymentRecord> recordListByPage = paymentRecordService.findPaymentRecordList(houseId);
       BigDecimal sum = BigDecimal.ZERO;
        for (PaymentRecord record : recordListByPage) {
            //必须要已缴费的才需要赔偿相应违约金
            if (PaymentStatusEnum.Payed.getCode().equals(record.getStatus())){
                //计算违约金的比例
                sum = sum.add(record.getHousePrice().multiply(Constant.DEFAULT_PLATFORM_LIQUIDATED_DAMAGES));
                //计算需要赔付租户的剩余租金，租金是一月一付
                Date date = new Date();
                long day = DateUtil.between(date, DateUtil.endOfMonth(date), DateUnit.DAY);
                Double remain = day / 30.0;
                BigDecimal remainPercent = new BigDecimal(String.format("%.2f", remain));
                sum = sum.add(remainPercent.multiply(record.getHousePrice()));
            }
        }
        return sum;
    }
}

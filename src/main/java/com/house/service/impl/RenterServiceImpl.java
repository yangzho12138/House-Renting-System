package com.house.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.house.dao.RenterDao;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.Renter;
import com.house.service.RenterService;
import com.house.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 租户服务
 * @since 2022/5/26
 **/
@Service
public class RenterServiceImpl implements RenterService {

    private final RenterDao renterDao;

    public RenterServiceImpl(RenterDao renterDao) {
        this.renterDao = renterDao;
    }

    @Override
    public Renter getRenterByRenterId(Integer renterId) {
        List<Renter> renterList =  renterDao.select(ImmutableMap.of("renterId", renterId));
        if (renterList.isEmpty()){
            return null;
        }
        return renterList.get(0);
    }

    @Override
    public void register(Renter renter) {
        renter.setRenterId(UserUtil.getUserInfo().getId());
        renter.setPhone(UserUtil.getUserInfo().getPhone());
        Integer insert = renterDao.insert(renter);
        if (insert < 1){
            throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION, "租户信息登记失败");
        }
    }

    @Override
    public void updateRenter(Renter renter) {
        try{
            Integer update = renterDao.update(renter);
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, "租户信息更新失败");
        }

    }

    @Override
    public void deleteRenter(Integer userId) {
        try{
            Integer delete = renterDao.delete(ImmutableList.of(userId));
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, "删除租户信息失败");
        }
    }
}

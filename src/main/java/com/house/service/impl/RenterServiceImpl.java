package com.house.service.impl;

import com.google.common.collect.ImmutableMap;
import com.house.dao.RenterDao;
import com.house.pojo.Renter;
import com.house.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 租户服务
 * @since 2022/5/26
 **/
@Service
public class RenterServiceImpl implements RenterService {

    @Autowired
    private RenterDao renterDao;

    @Override
    public Renter getRenterByRenterId(Integer renterId) {
        List<Renter> renterList =  renterDao.select(ImmutableMap.of("renterId", renterId));
        if (renterList.isEmpty()){
            return null;
        }
        return renterList.get(0);
    }
}

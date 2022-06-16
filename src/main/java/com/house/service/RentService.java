package com.house.service;

import com.google.common.collect.ImmutableMap;
import com.house.pojo.HouseRentRelation;

import java.util.List;
import java.util.Map;

public interface RentService {

    void rent(Integer houseId);

    void forceWithdraw(Integer houseId);

    void deleteHouseRentRelation(Integer houseId, Integer renterId);

    void deleteHouseRentRelation(Integer renterId);

    List<HouseRentRelation> getRentRelationByRenterId(Map<String, Object> params);
}

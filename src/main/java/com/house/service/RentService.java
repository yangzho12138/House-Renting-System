package com.house.service;

public interface RentService {

    void rent(Integer houseId);

    void forceWithdraw(Integer houseId);

    void deleteHouseRentRelation(Integer houseId, Integer renterId);
}

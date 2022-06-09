package com.house.service;

import com.house.common.Page;
import com.house.pojo.House;
import com.house.pojo.HouseView;

import java.util.List;
import java.util.Map;

public interface HouseService {

    Page<House> findHouseListByPage(Map<String, Object> params);

    Page<HouseView> findHouseViewListPage(Map<String, Object> params);

    List<House> findHouseListByCondition(Map<String, Object> params);

    void addHouse(House house);

    void addHouseView(HouseView houseView);

    void deleteHouse(Integer houseId);

    void updateHouse(House house);

    House getHouseById(Integer houseId);

    void deleteHouses(List<Integer> houseIds);
}

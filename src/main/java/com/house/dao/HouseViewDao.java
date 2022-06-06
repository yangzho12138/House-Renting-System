package com.house.dao;

import com.house.pojo.HouseView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HouseViewDao {

    List<HouseView> select(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    Integer insert(HouseView houseView);

    Integer update(HouseView houseView);

    Integer delete(List<Integer> houseViewIds);
}

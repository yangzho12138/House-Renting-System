package com.house.dao;

import com.house.pojo.House;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface HouseDao {

     List<House> select(Map<String, Object> params);

     Integer insert(House house);

     Integer update(House house);

     Integer delete(List<Integer> houseIds);

     Integer count(Map<String, Object> params);
}

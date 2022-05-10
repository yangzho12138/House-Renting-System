package com.house.dao;

import com.house.pojo.HouseRentRelation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HouseRentRelationDao {

    List<HouseRentRelation> select(Map<String, Object> params);

    Integer insert(List<HouseRentRelation> houseRentRelations);

    Integer update(HouseRentRelation houseRentRelation);

    Integer delete(Map<String, Object> params);

    Long count(Map<String, Object> params);
}

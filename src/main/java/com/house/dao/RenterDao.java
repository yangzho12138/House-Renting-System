package com.house.dao;

import com.house.pojo.Renter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RenterDao {

    List<Renter> select(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    Integer update(Renter renter);

    Integer insert(Renter renter);

    Integer delete(@Param("renterIds") List<Integer> renterIds);
}

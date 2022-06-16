package com.house.dao;

import com.house.pojo.Owner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OwnerDao {

    List<Owner> select(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    Integer update(Owner owner);

    Integer updateByAdmin(Owner owner);

    Integer insert(Owner owner);

    Integer delete(@Param("ownerIds") List<Integer> ownerIds);
}

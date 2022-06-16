package com.house.dao;

import com.house.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserDao {

	List<User> select(Map<String, Object> params);

	Integer count(Map<String, Object> params);

	Integer update(User user);

	Integer insert(User user);

	Integer delete(@Param("userIds") List<Integer> userIds);

}

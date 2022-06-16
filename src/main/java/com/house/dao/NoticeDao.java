package com.house.dao;

import com.house.pojo.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NoticeDao {

    List<Notice> select(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    Integer insert(Notice notice);

    Integer update(Notice notice);

    Integer delete(@Param("noticeIds") List<Integer> noticeIds);

    Integer updateByUserId(Notice notice);
}

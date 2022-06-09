package com.house.service;

import com.house.pojo.Notice;

import java.util.List;

/**
 * 消息调用服务
 **/
public interface NoticeService {

    List<Integer> getNoticeIdsByRenterId(Integer userId);

    void deleteByNoticeIds(List<Integer> noticeIds);

    void updateByUserId(Notice notice);
}

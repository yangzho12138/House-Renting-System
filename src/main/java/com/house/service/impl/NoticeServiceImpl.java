package com.house.service.impl;

import com.google.common.collect.ImmutableMap;
import com.house.dao.NoticeDao;
import com.house.pojo.Notice;
import com.house.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息通知服务
 **/
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDao noticeDao;

    public NoticeServiceImpl(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public List<Integer> getNoticeIdsByRenterId(Integer userId) {
        return noticeDao.select(ImmutableMap.of("userId", userId))
                .stream().map(Notice::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByNoticeIds(List<Integer> noticeIds) {
        noticeDao.delete(noticeIds);
    }
}
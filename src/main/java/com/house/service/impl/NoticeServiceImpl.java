package com.house.service.impl;

import com.google.common.collect.ImmutableMap;
import com.house.dao.NoticeDao;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.Notice;
import com.house.service.NoticeService;
import com.house.utils.EmailSendUtil;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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

    @Override
    public void updateByUserId(Notice notice) {
        Integer update = noticeDao.updateByUserId(notice);
        if (update < 1){
            throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION, "更新消息通知失败");
        }
    }

    @Override
    public void sendEmail(String email) throws MessagingException {
        EmailSendUtil.send(email);
    }
}

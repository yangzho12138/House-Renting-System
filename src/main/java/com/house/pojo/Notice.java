package com.house.pojo;

import java.sql.Date;

/**
 * @version 通知信息实体类
 * @since 2022/5/9
 **/
public class Notice {
    /**
     * 通知信息 ID
     **/
    private Integer id;

    /**
     * 通知信息内容
     **/
    private String content;

    /**
     * 通知信息起始时间
     **/
    private Date fromDate;

    /**
     * 通知信息结束时间
     **/
    private Date toDate;

    /**
     * 通知对象电话号码
     **/
    private String phone;

    /**
     * 通知对象邮箱
     **/
    private String email;

    /**
     * 其他信息
     **/
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

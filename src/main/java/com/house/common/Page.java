package com.house.common;

import java.io.Serializable;
import java.util.List;

/**
 * @version 查询返回的页数结果
 * @since 2022/5/10
 **/
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 2923477771741710108L;

    /**
     * 总记录数
     **/
    private int totalCount;

    /**
     * 每页记录数
     **/
    private int pageSize = 10;

    /**
     * 总页数
     **/
    private int totalPage;

    /**
     * 当前页数
     **/
    private int currPage = 1;

    /**
     * 列表数据
     **/
    private List<T> list;

    /**
     * 分页
     * @param list        列表数据
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param currPage    当前页数
     */
    public Page(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

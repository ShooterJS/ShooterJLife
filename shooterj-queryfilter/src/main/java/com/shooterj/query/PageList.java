package com.shooterj.query;

import com.github.pagehelper.Page;
import com.shooterj.query.PageBean;

import java.util.List;
import java.util.Map;

public class PageList<E> {
    List<E> rows;
    long total;
    int page;
    int pageSize;
    Map<String, Object> sumMap;

    public PageList() {
    }

    public PageList(List<E> c) {
        if (c instanceof Page) {
            Page<E> page = (Page<E>) c;
            this.rows = page.getResult();
            this.total = page.getTotal();
            this.page = page.getPageNum();
            this.pageSize = page.getPageSize();
        } else {
            this.rows = c;
            this.total = this.rows.size();
        }
    }

    public PageList(Page<E> page) {
        this.rows = page.getResult();
        this.total = page.getTotal();
        this.page = page.getPageNum();
        this.pageSize = page.getPageSize();
    }

    public PageList(List<E> list, PageBean pageBean) {
        if (pageBean == null) {
            pageBean = new PageBean(1, Integer.MAX_VALUE - 1, false);
        }
        int total = list.size();
        int pageNo = Math.max(1, pageBean.getPage());
        int pageSize = Math.max(1, pageBean.getPageSize());
        int toIndex = Math.min(pageSize * pageNo, total);
        int fromIndex = Math.min(pageSize * (pageNo - 1), toIndex);

        this.rows = list.subList(fromIndex, toIndex);
        this.total = total;
        this.page = pageNo;
        this.pageSize = pageSize;
    }

    public PageList(List<E> rows, long total, int page, int pageSize) {
        this.rows = rows;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page = page == 0 ? 1 : page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize = pageSize == 0 ? 20 : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setSumMap(Map<String, Object> sumMap) {
        this.sumMap = sumMap;
    }

    public Map<String, Object> getSumMap() {
        return sumMap;
    }
}

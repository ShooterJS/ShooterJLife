package com.shooterj.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;

public class PageBean extends RowBounds implements Serializable {

    private static final long serialVersionUID = -965962607403458874L;

    /** 默认每页显示的记录数 */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /** 默认显示第一页 */
    public final static int NO_PAGE = 1;

    /** 页号 */
    protected int page = NO_PAGE;
    /** 分页大小 */
    protected int pageSize = DEFAULT_PAGE_SIZE;
    /** 是否显示总条数 */
    protected boolean showTotal = true;

    public static PageBean MAX_PAGE_BEAN = new PageBean(1, Integer.MAX_VALUE, false);

    public PageBean() {}

    public PageBean(Integer page) {
        this.page = page;
    }

    public PageBean(Boolean showTotal) {
        this.showTotal = showTotal;
    }

    public PageBean(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageBean(Integer page, Integer pageSize, Boolean showTotal) {
        this.page = page;
        this.pageSize = pageSize;
        this.showTotal = showTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public boolean showTotal() {
        return showTotal;
    }

    public void setShowTotal(boolean showTotal) {
        this.showTotal = showTotal;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @JsonIgnore
    @Override
    public int getLimit() {
        return this.pageSize;
    }


    @JsonIgnore
    @Override
    public int getOffset() {
        return this.page > 0 ? (this.page - 1) * this.pageSize : 0;
    }


}

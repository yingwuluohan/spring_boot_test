package com.unisound.iot.common.modle.page;


import java.util.List;

/**
 * Created by Admin on 2017/10/16.
 */
public class Page<T> {

    /**
     * 每页大小
     */
    private int pageSize = 20;
    /**
     * 当前页
     */
    private int currentPage = 0;


    /**
     * 总行数
     */
    private int totalLine;
    /**
     * 总页数
     */
    private int totalPageSize;
    /**
     * 结果集
     */
    private List<T> list;

    public int getTotalPageSize() {
        int count = totalLine / pageSize;
        if( totalLine % pageSize != 0 ){
            count = count +1 ;
        }
        totalPageSize = count;
        return totalPageSize ;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public int getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(int totalLine) {
        this.totalLine = totalLine;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;

        Page<?> page = (Page<?>) o;

        return list.equals(page.list);

    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}

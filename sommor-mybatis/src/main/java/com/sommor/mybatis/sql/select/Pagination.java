package com.sommor.mybatis.sql.select;

/**
 * @author wuyu
 * @since 2019/1/27
 */
public class Pagination extends Limitation {

    public static int defaultPageSize = 20;

    public static int maxPageSize = 50;

    private int page;

    private int pageSize;

    public Pagination(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public static Pagination of(Integer page) {
        return of(page, null);
    }

    public static Pagination of(Integer page, Integer pageSize) {
        page = page == null || page < 1 ? 1 : page;
        pageSize = pageSize == null || (pageSize < 1 || pageSize > maxPageSize) ? defaultPageSize : pageSize;

        return new Pagination(page, pageSize);
    }

    public int getPage() {
        return page;
    }

    public Integer getOffset() {
        return (page-1) * pageSize;
    }

    @Override
    public Integer getCount() {
        return this.getPageSize();
    }

    public int getPageSize() {
        return pageSize;
    }
}

package com.sommor.mybatis.query;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/25
 */
public class QueryOptions {

    private Boolean pageableEnabled;

    private Boolean orderlyEnabled;

    public Boolean getPageableEnabled() {
        return pageableEnabled;
    }

    public void setPageableEnabled(Boolean pageableEnabled) {
        this.pageableEnabled = pageableEnabled;
    }

    public Boolean getOrderlyEnabled() {
        return orderlyEnabled;
    }

    public void setOrderlyEnabled(Boolean orderlyEnabled) {
        this.orderlyEnabled = orderlyEnabled;
    }
}

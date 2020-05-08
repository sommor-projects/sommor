package com.sommor.bundles.mall.order.model;

import com.sommor.core.component.status.StatusEnum;
import com.sommor.core.component.status.StatusManager;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
public enum ShippingStatusEnum implements StatusEnum {
    UNSHIPPED(0, "待发货"),
    SHIPPED(1, "已发货"),
    RECEIVED(2, "已签收")
    ;

    private int code;

    private String title;

    ShippingStatusEnum(int code, String title) {
        this.code = code;
        this.title = title;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return title;
    }
}

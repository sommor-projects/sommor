package com.sommor.bundles.mall.order.model;

import com.sommor.core.component.status.StatusEnum;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
public enum PayStatusEnum implements StatusEnum {
    UNPAID(0, "未付款"),
    PAID(1, "已付款"),
    REFUND(2, "已退款")
    ;

    private int code;

    private String title;

    PayStatusEnum(int code, String title) {
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

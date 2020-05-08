package com.sommor.bundles.mall.order.model;

import com.sommor.core.component.status.StatusEnum;
import com.sommor.core.component.status.StatusManager;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
public enum OrderStatusEnum implements StatusEnum {
    CREATED(0, "待确认"),
    CONFIRMED(1, "已确认"),
    SUCCESS(2, "交易成功"),
    CLOSED(3, "交易关闭"),
    CANCEL(3, "已取消"),
    ;

    private int code;

    private String title;

    OrderStatusEnum(int code, String title) {
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

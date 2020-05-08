package com.sommor.bundles.outline.model;

import com.sommor.core.component.status.StatusEnum;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
public enum AccessKeyStatus implements StatusEnum {
    UNUSED(0, "未使用"),
    USING(1, "使用中"),
    DELETED(2, "已删除")
    ;

    private Integer code;

    private String title;

    AccessKeyStatus(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return title;
    }
}

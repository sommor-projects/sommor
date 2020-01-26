package com.sommor.scaffold.entity;

import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
public interface EntityLifecycle {

    default void onSaving(BaseEntity original) {
    }

    default void onSaved(BaseEntity original) {
    }

}

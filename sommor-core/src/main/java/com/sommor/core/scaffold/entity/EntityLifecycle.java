package com.sommor.core.scaffold.entity;

import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
public interface EntityLifecycle {

    default void onSaving(Object original) {
    }

    default void onSaved(Object original) {
    }

}

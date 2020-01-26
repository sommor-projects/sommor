package com.sommor.scaffold.view.field;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Extension(name = "字段拦截器")
public interface FieldInterceptor {

    default void interceptOnInit(FieldDefinition definition) {
    }

    default void interceptOnQuery(FieldQueryContext ctx) {
    }

    default Object interceptOnFill(FieldContext ctx) {
        return null;
    }

    default void interceptOnFormSaving(FieldSaveContext ctx) {
    }

    default void interceptOnFormSaved(FieldSaveContext ctx) {
    }
}

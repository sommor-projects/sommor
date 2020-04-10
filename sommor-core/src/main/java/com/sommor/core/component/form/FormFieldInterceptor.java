package com.sommor.core.component.form;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Extension(name = "表单字段拦截器")
public interface FormFieldInterceptor {

    default void interceptOnFormSaving(FieldSaveContext ctx) {
    }

    default void interceptOnFormSaved(FieldSaveContext ctx) {
    }
}

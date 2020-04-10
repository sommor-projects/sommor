package com.sommor.core.component.form;

import com.sommor.core.curd.query.FieldContext;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Extension(name = "表单字段处理器")
public interface FormFieldProcessor<FA> {

    default void processOnFormValidate(FA fa, FieldContext ctx) {
    }

    default void processOnFormSaving(FA fa, FieldSaveContext ctx) {
    }

    default void processOnFormSaved(FA fa, FieldSaveContext ctx) {
    }
}

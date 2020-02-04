package com.sommor.core.view.field;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Extension(name = "字段处理器")
public interface FieldProcessor<FieldConfig> {

    default Object processOnInit(FieldConfig config, FieldDefinition fd) {
        return null;
    }

    default Object processOnFill(FieldConfig config, FieldContext ctx) {
        return null;
    }

    default void processOnFormRender(FieldConfig config, FieldRenderContext ctx) {
    }

    default void processOnQuery(FieldConfig config, FieldQueryContext ctx) {
    }

    default void processOnFormValidate(FieldConfig config, FieldContext ctx) {
    }

    default void processOnFormSaving(FieldConfig config, FieldSaveContext ctx) {
    }

    default void processOnFormSaved(FieldConfig config, FieldSaveContext ctx) {
    }
}

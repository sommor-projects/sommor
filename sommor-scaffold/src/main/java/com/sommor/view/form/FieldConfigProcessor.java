package com.sommor.view.form;

import com.sommor.extensibility.config.Extension;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.view.FieldView;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Extension(name = "字段处理器")
public interface FieldConfigProcessor<ViewConfig> {

    default void processOnFieldInit(ViewConfig viewConfig, FormFieldDefinition definition) {
    }

    default void processOnFormRender(ViewConfig viewConfig, FieldView view, EntityForm form) {
    }

    default void processOnFormSave(ViewConfig viewConfig, Object value, BaseEntity entity, BaseEntity originalEntity, EntityForm form) {
    }
}

package com.sommor.view.form;

import com.sommor.extensibility.config.Extension;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.view.FormView;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
@Extension(name = "字段类型处理器")
public interface FieldTypeProcessor<Type> {

    default void processOnFormRender(Type value, BaseEntity entity, EntityForm form, FormView formView) {
    }

    default void processOnFormSave(Type value, BaseEntity entity, BaseEntity originEntity, EntityForm form) {
    }
}

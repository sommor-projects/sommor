package com.sommor.view.form;

import com.sommor.extensibility.config.Extension;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Extension(name = "表单字段解析器")
public interface FormFieldResolver<ViewConfig> {

    default void resolveOnInit(ViewConfig viewConfig, FormFieldDefinition definition) {
    }

    default void resolveOnRender(ViewConfig viewConfig, FieldView view, FormView formView, FormField formField) {
    }
}

package com.sommor.view.form;

import com.sommor.extensibility.config.Extension;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.view.FieldView;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
@Extension(name = "字段渲染")
public interface FieldViewProcessor<V extends FieldView> {

    default void processOnFormRender(V view, EntityForm form) {
    }

}

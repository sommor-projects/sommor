package com.sommor.core.component.form.extension;

import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Extension(name = "表单字段保存后处理器")
public interface FormFieldSavedProcessor<FA> {
    void processOnFormSaved(FA fa, FieldSaveContext ctx);
}

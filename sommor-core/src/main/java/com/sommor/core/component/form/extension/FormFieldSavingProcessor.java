package com.sommor.core.component.form.extension;

import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Extension(name = "表单字段保存前处理器")
public interface FormFieldSavingProcessor<FA> {
    void processOnFormSaving(FA fa, FieldSaveContext ctx);
}

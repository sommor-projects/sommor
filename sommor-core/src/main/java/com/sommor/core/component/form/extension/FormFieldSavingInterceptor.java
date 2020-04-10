package com.sommor.core.component.form.extension;

import com.sommor.core.component.form.FieldSaveContext;
import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Extension(name = "表单字段保存前拦截器")
public interface FormFieldSavingInterceptor {
    void interceptOnFormSaving(FieldSaveContext ctx);
}
